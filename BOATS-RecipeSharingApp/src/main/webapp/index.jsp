<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recipe Sharing Platform - Sign Up</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        form {
            margin-top: 20px;
        }
        input[type="text"],
        input[type="email"],
        input[type="password"],
        textarea {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 16px;
        }
        textarea {
            height: 100px;
        }
        input[type="submit"] {
            width: 100%;
            background-color: #4caf50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Sign Up</h2>
    <form action="<%= request.getContextPath() %>/signup" method="post">
        <label for="name">Name</label>
        <input type="text" id="name" name="name" required>

        <label for="surname">Surname</label>
        <input type="text" id="surname" name="surname" required>

        <label for="email">Email</label>
        <input type="email" id="email" name="email" required>

        <label for="password">Password</label>
        <input type="password" id="password" name="password" required>

        <label for="username">Username</label>
        <input type="text" id="username" name="username" required>

        <label for="bio">Bio</label>
        <textarea id="bio" name="bio"></textarea>

        <label for="image">Image</label>
        <input type="file" id="image" name="image" accept="image/*">

        <input type="submit" value="Sign Up">


    </form>
    <div class="Login-link">
        <p>Already a user? <a href="Login.jsp">Login</a></p>
    </div>
</div>
</body>
</html>
