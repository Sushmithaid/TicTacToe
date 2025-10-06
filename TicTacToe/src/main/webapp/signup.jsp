<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>TicTacToe - Sign Up</title>
  <link rel="stylesheet" href="auth.css">
  
  <script>
  function validateForm() {
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;

    if (password !== confirmPassword) {
      alert("Passwords do not match! Please try again.");
      return false; // stop form submission
    }
    return true; // allow form submission
  }
</script>
  
  
  
</head>
<body>
  <div class="game-symbols">
    <span class="symbol x" style="top: 10%; left: 10%;">X</span>
    <span class="symbol o" style="top: 15%; right: 15%;">O</span>
    <span class="symbol x" style="bottom: 20%; left: 15%;">X</span>
    <span class="symbol o" style="bottom: 15%; right: 10%;">O</span>
    <span class="symbol x" style="top: 60%; left: 5%;">X</span>
    <span class="symbol o" style="top: 70%; right: 8%;">O</span>
  </div>
  <div class="auth-container">
    <div class="auth-card">
      <div class="auth-header">
        <h1>TicTacToe</h1>
        <p>Create your account to start playing</p>
      </div>

      <form class="auth-form" action="sign-up" method="POST" onsubmit="return validateForm()">
        <div class="form-group">
          <label for="username">Username</label>
          <input type="text" id="username" name="username" placeholder="Enter your username" required>
        </div>

        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" placeholder="Enter your email" required>
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" placeholder="Enter your password" required>
        </div>

        <div class="form-group">
          <label for="confirm-password">Confirm Password</label>
          <input type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm your password" required>        
        </div>

        <button type="submit" class="btn-primary">Sign Up</button>
        
      </form>

      <div class="auth-footer">
        <p>Already have an account?<a href="login.jsp">Login here</a></p>
      </div>
      
    </div>
  </div>
</body>
</html>
