const X_CLASS = 'x';
const O_CLASS = 'o';
const WINNING_COMBINATIONS = [
  [0, 1, 2],
  [3, 4, 5],
  [6, 7, 8],
  [0, 3, 6],
  [1, 4, 7],
  [2, 5, 8],
  [0, 4, 8],
  [2, 4, 6]
];

let currentPlayer = X_CLASS;
let gameMode = null;
let player1Name = '';
let player2Name = '';
let scoreX = 0;
let scoreO = 0;
let gameActive = false;
let board = ['', '', '', '', '', '', '', '', ''];

const modeSelection = document.getElementById('mode-selection');
const playerSetup = document.getElementById('player-setup');
const gameContainer = document.getElementById('game-container');
const winnerModal = document.getElementById('winner-modal');

const aiModeBtn = document.getElementById('ai-mode-btn');
const twoPlayerBtn = document.getElementById('two-player-btn');
const backToModeBtn = document.getElementById('back-to-mode');
const startGameBtn = document.getElementById('start-game');
const backToMenuBtn = document.getElementById('back-to-menu');
const resetGameBtn = document.getElementById('reset-game');
const playAgainBtn = document.getElementById('play-again');
const changeModeBtn = document.getElementById('change-mode');

const player1Input = document.getElementById('player1-name');
const player2Input = document.getElementById('player2-name');
const player2InputGroup = document.getElementById('player2-input-group');

const boardElement = document.getElementById('board');
const cells = document.querySelectorAll('[data-cell]');
const turnIndicator = document.getElementById('turn-indicator');
const player1Label = document.getElementById('player1-label');
const player2Label = document.getElementById('player2-label');
const scoreXElement = document.getElementById('score-x');
const scoreOElement = document.getElementById('score-o');
const winnerText = document.getElementById('winner-text');

aiModeBtn.addEventListener('click', () => selectMode('ai'));
twoPlayerBtn.addEventListener('click', () => selectMode('two-player'));
backToModeBtn.addEventListener('click', showModeSelection);
startGameBtn.addEventListener('click', startGame);
backToMenuBtn.addEventListener('click', showModeSelection);
resetGameBtn.addEventListener('click', resetBoard);
playAgainBtn.addEventListener('click', () => {
  winnerModal.classList.remove('active');
  resetBoard();
});
changeModeBtn.addEventListener('click', () => {
  winnerModal.classList.remove('active');
  showModeSelection();
});

function selectMode(mode) {
  gameMode = mode;
  modeSelection.classList.remove('active');
  playerSetup.classList.add('active');

  if (mode === 'ai') {
    player2InputGroup.style.display = 'none';
    player2Input.value = 'AI';
  } else {
    player2InputGroup.style.display = 'flex';
    player2Input.value = '';
  }

  player1Input.focus();
}

function showModeSelection() {
  gameContainer.classList.remove('active');
  playerSetup.classList.remove('active');
  modeSelection.classList.add('active');
  scoreX = 0;
  scoreO = 0;
  updateScores();
}

function startGame() {
  player1Name = player1Input.value.trim() || 'Player X';
  player2Name = gameMode === 'ai' ? 'AI' : (player2Input.value.trim() || 'Player O');

  player1Label.textContent = player1Name;
  player2Label.textContent = player2Name;

  playerSetup.classList.remove('active');
  gameContainer.classList.add('active');

  gameActive = true;
  currentPlayer = X_CLASS;
  board = ['', '', '', '', '', '', '', '', ''];

  cells.forEach(cell => {
    cell.classList.remove(X_CLASS, O_CLASS, 'winner');
    cell.textContent = '';
    cell.addEventListener('click', handleCellClick, { once: true });
  });

  updateTurnIndicator();
}

function handleCellClick(e) {
  if (!gameActive) return;

  const cell = e.target;
  const cellIndex = Array.from(cells).indexOf(cell);

  if (board[cellIndex] !== '') return;

  placeMark(cell, cellIndex, currentPlayer);

  if (checkWin(currentPlayer)) {
    endGame(false, currentPlayer);
  } else if (isDraw()) {
    endGame(true);
  } else {
    swapTurns();

    if (gameMode === 'ai' && currentPlayer === O_CLASS && gameActive) {
      setTimeout(makeAIMove, 500);
    }
  }
}

function placeMark(cell, index, player) {
  console.log(`Move placed by ${player === 'x' ? player1Name : player2Name}: cell ${index}`);
  board[index] = player;
  cell.classList.add(player);
  cell.textContent = player.toUpperCase();
  console.log(`Board state: ${board.join(' | ')}`);
}


function swapTurns() {
  currentPlayer = currentPlayer === X_CLASS ? O_CLASS : X_CLASS;
  updateTurnIndicator();
}

function updateTurnIndicator() {
  const playerName = currentPlayer === X_CLASS ? player1Name : player2Name;
  turnIndicator.textContent = playerName;
  turnIndicator.className = currentPlayer === X_CLASS ? 'x-turn' : 'o-turn';
}

function checkWin(player) {
  return WINNING_COMBINATIONS.some(combination => {
    return combination.every(index => {
      return board[index] === player;
    });
  });
}

function isDraw() {
  return board.every(cell => cell !== '');
}

function endGame(draw, winner = null) {
  gameActive = false;

  if (draw) {
    winnerText.textContent = "It's a Draw!";
    console.log("Result: It's a Draw!");
  } else {
    const winnerName = winner === X_CLASS ? player1Name : player2Name;
    winnerText.textContent = `${winnerName} Wins!`;
    console.log(`🎉 Winner: ${winnerName}`);

    if (winner === X_CLASS) {
      scoreX++;
    } else {
      scoreO++;
    }

    updateScores();
    highlightWinningCells(winner);

    // ✅ Save result to backend
    saveWinnerToDB(winnerName, player1Name, player2Name);
  }

  setTimeout(() => {
    winnerModal.classList.add('active');
  }, 500);
}



function highlightWinningCells(player) {
  WINNING_COMBINATIONS.forEach(combination => {
    if (combination.every(index => board[index] === player)) {
      combination.forEach(index => {
        cells[index].classList.add('winner');
      });
    }
  });
}

function updateScores() {
  scoreXElement.textContent = scoreX;
  scoreOElement.textContent = scoreO;
}

function resetBoard() {
  gameActive = true;
  currentPlayer = X_CLASS;
  board = ['', '', '', '', '', '', '', '', ''];

  cells.forEach(cell => {
    cell.classList.remove(X_CLASS, O_CLASS, 'winner');
    cell.textContent = '';
    cell.removeEventListener('click', handleCellClick);
    cell.addEventListener('click', handleCellClick, { once: true });
  });

  updateTurnIndicator();
}

function makeAIMove() {
  if (!gameActive) return;

  const bestMove = getBestMove();

  if (bestMove !== -1) {
    const cell = cells[bestMove];
    placeMark(cell, bestMove, O_CLASS);

    if (checkWin(O_CLASS)) {
      endGame(false, O_CLASS);
    } else if (isDraw()) {
      endGame(true);
    } else {
      swapTurns();
    }
  }
}

function getBestMove() {
  if (board.filter(cell => cell !== '').length === 0) {
    const corners = [0, 2, 6, 8];
    return corners[Math.floor(Math.random() * corners.length)];
  }

  for (let i = 0; i < 9; i++) {
    if (board[i] === '') {
      board[i] = O_CLASS;
      if (checkWin(O_CLASS)) {
        board[i] = '';
        return i;
      }
      board[i] = '';
    }
  }

  for (let i = 0; i < 9; i++) {
    if (board[i] === '') {
      board[i] = X_CLASS;
      if (checkWin(X_CLASS)) {
        board[i] = '';
        return i;
      }
      board[i] = '';
    }
  }

  if (board[4] === '') {
    return 4;
  }

  const corners = [0, 2, 6, 8];
  const availableCorners = corners.filter(i => board[i] === '');
  if (availableCorners.length > 0) {
    return availableCorners[Math.floor(Math.random() * availableCorners.length)];
  }

  const availableMoves = board.map((cell, index) => cell === '' ? index : null).filter(val => val !== null);
  return availableMoves.length > 0 ? availableMoves[Math.floor(Math.random() * availableMoves.length)] : -1;
}

modeSelection.classList.add('active');

function saveWinnerToDB(winnerName, playerX, playerO) {
  fetch('tictactoe', {
    method: 'POST',
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    body: `winner=${winnerName}&playerX=${playerX}&playerO=${playerO}`
  })
  .then(res => res.json())
  .then(data => {
    console.log(data.message);
  })
  .catch(err => console.error('Error saving winner:', err));
}

