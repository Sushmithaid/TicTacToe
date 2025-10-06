<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>TicTacToe - Login</title>
  <link rel="stylesheet" href="auth.css">
    
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
        <p>Welcome back! Login to continue playing</p>
      </div>

      <form class="auth-form" action="log-in" method="POST">
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" placeholder="Enter your email" required>
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" placeholder="Enter your password" required>
        </div>

        <div class="form-options">
          <label class="checkbox-label">
            <input type="checkbox" name="remember">
            <span>Remember me</span>
          </label>
          <a href="#" class="forgot-password">Forgot Password?</a>
        </div>

        <button type="submit" class="btn-primary"><a href="home.jsp">Login</a></button>
      </form>

      <div class="auth-footer">
        <p>Don't have an account? <a href="signup.html">Sign up here</a></p>
      </div>
      
    </div>
  </div>
</body>
</html>
