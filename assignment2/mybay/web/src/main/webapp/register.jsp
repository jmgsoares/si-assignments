<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>MyBay - Register</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>


<form>
    <label>
        <p class="label-txt">ENTER YOUR NAME</p>
        <input type="text" class="input" name="name">
        <div class="line-box">
            <div class="line"></div>
        </div>
    </label>

    <label>
        <p class="label-txt">ENTER YOUR EMAIL</p>
        <input type="text" class="input" name="email">
        <div class="line-box">
            <div class="line"></div>
        </div>
    </label>

    <label>
        <p class="label-txt">ENTER YOUR PASSWORD</p>
        <input type="password" class="input" name="password">
        <div class="line-box">
            <div class="line"></div>
        </div>
    </label>

    <label>
        <p class="label-txt">ENTER YOUR COUNTRY</p>
        <div class="form-group">
            <label for="sel1"></label>
            <select class="form-control" id="sel1">
                <option value="portugal">Portugal</option>
                <option value="spain">Spain</option>
                <option value="france">France</option>
                <option value="england">England</option>
                <option value="germany">Germany</option>
                <option value="belgium">Belgium</option>
                <option value="switzerland">Switzerland</option>
            </select>
        </div>
    </label>


    <button type="submit">submit</button>
    <p><br>Already registered? <a href="login.jsp">Login here!</a></p>
</form>

</body>
</html>