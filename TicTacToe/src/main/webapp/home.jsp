<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="icon" type="image/svg+xml" href="/vite.svg" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>TicTacToe - Play Now!</title>
    <link rel="stylesheet" href="style.css" />
  </head>
  <body>
    <div class="game-wrapper">
      <!-- Mode Selection Screen -->
      <div id="mode-selection" class="mode-selection active">
        <div class="logo-container">
          <h1 class="game-title">
            <span class="x-letter">X</span>
            <span class="vs">vs</span>
            <span class="o-letter">O</span>
          </h1>
          <p class="subtitle">Choose Your Battle</p>
        </div>

        <div class="mode-cards">
          <div class="mode-card" id="ai-mode-btn">
            <div class="icon">🤖</div>
            <h2>VS AI</h2>
            <p>Challenge the Computer</p>
            <div class="card-glow"></div>
          </div>

          <div class="mode-card" id="two-player-btn">
            <div class="icon">👥</div>
            <h2>2 Players</h2>
            <p>Play with a Friend</p>
            <div class="card-glow"></div>
          </div>
        </div>
      </div>

      <!-- Player Names Input -->
      <div id="player-setup" class="player-setup">
        <h2 class="setup-title">Player Details</h2>

        <div class="input-group">
          <label for="player1-name">
            <span class="player-badge x-badge">X</span>
            Player 1 Name
          </label>
          <input type="text" id="player1-name" placeholder="Enter name" maxlength="15" />
        </div>

        <div class="input-group" id="player2-input-group">
          <label for="player2-name">
            <span class="player-badge o-badge">O</span>
            Player 2 Name
          </label>
          <input type="text" id="player2-name" placeholder="Enter name" maxlength="15" />
        </div>

        <button class="start-game-btn" id="start-game">Start Game</button>
        <button class="back-btn" id="back-to-mode">Back</button>
      </div>

      <!-- Game Board -->
      <div id="game-container" class="game-container">
        <div class="game-header">
          <button class="menu-btn" id="back-to-menu">← Menu</button>
          <div class="score-board">
            <div class="score-item x-score">
              <div class="score-label" id="player1-label">Player X</div>
              <div class="score-value" id="score-x">0</div>
            </div>
            <div class="score-divider">:</div>
            <div class="score-item o-score">
              <div class="score-label" id="player2-label">Player O</div>
              <div class="score-value" id="score-o">0</div>
            </div>
          </div>
        </div>

        <div class="current-turn">
          <span id="turn-indicator">X</span>'s Turn
        </div>

        <div class="board-container">
          <div class="board" id="board">
            <div class="cell" data-cell></div>
            <div class="cell" data-cell></div>
            <div class="cell" data-cell></div>
            <div class="cell" data-cell></div>
            <div class="cell" data-cell></div>
            <div class="cell" data-cell></div>
            <div class="cell" data-cell></div>
            <div class="cell" data-cell></div>
            <div class="cell" data-cell></div>
          </div>
          <div class="winning-line" id="winning-line"></div>
        </div>

        <button class="reset-btn" id="reset-game">Play Again</button>
      </div>

      <!-- Winner Modal -->
      <div id="winner-modal" class="modal">
        <div class="modal-content">
          <div class="winner-animation">
            <div class="confetti"></div>
            <div class="trophy">🏆</div>
          </div>
          <h2 id="winner-text">Player X Wins!</h2>
          <button class="modal-btn primary" id="play-again">Play Again</button>
          <button class="modal-btn secondary" id="change-mode">Change Mode</button>
        </div>
      </div>
    </div>

    <script type="module" src="main.js"></script>
  </body>
</html>
