<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Training project "Inquirer"</title>
    <base href="#">
    <!--[if lte IE 6]></base><![endif]-->
    <link rel="shortcut icon" href="images/favicon.ico">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta name="keywords" content="testing online DAJ Group create test">
    <meta name="author" content="DAJ Group">
    <meta name="description"
          content="Conditional title: Online testing creating system. Target: Creating a completely developed system of online testing. The system will have the admin part and the client part.">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <!-- <link rel="stylesheet" href="css/reset.min.css"> or reset.css -->
    <link rel='stylesheet' href='http://cdnjs.cloudflare.com/ajax/libs/normalize/3.0.1/normalize.min.css'>
    <!-- <link rel="stylesheet" href="css/normalize.css"> or normalize.min.css -->
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <link rel="stylesheet" href="css/style.css">
    <link href="css/jquery.formstyler.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="//cdnjs.cloudflare.com/ajax/libs/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/respond.js/1.4.2/respond.js"></script>
    <![endif]-->
</head>
<body>
<div id="main">
    <div id="content" class="clearfix">
        <header>
            <a id="logo" href="#" title="DAJ Group"></a>
            <nav>
                <ul class="nav-list">
                    <li><a title="Вход" href="#loginformwrapper" id="menu_login">Вход</a></li>
                    <li><a title="Регистрация" href="#regformwrapper" id="menu_registration">Регистрация</a></li>
                    <li><a title="О проекте" href="#">О проекте</a></li>
                    <li><a title="FAQ" href="#">FAQ</a></li>
                </ul>
            </nav>

            <!-- Popups -->
            <div class="allforms" id="loginformwrapper">
                <div class="popup_close" id="closeloginform"></div>
                <form id="loginform" action="${pageContext.request.contextPath}/AuthServlet" method="post" name="login"
                      autocomplete="on">
                    <label for="autorizlogin">Логин:
                        <small>or e-mail; english please</small>
                    </label>
                    <input type="text" id="autorizlogin" name="UserName">
                    <label for="autorizpass">Пароль:
                        <small>english please</small>
                    </label>
                    <input type="password" id="autorizpass" name="UserPWD">
                    <!--<input class="sendform" type="submit" value="Войти">-->
                    <button class="sendform" type="submit">Войти</button>
                </form>
            </div>

            <div class="allforms" id="regformwrapper">
                <div class="popup_close" id="closeregform"></div>
                <form id="regform" action="${pageContext.request.contextPath}/RegServlet" method="post"
                      name="registration" autocomplete="on">
                    <label for="registrlogin">Логин:
                        <small>english please</small>
                    </label>
                    <input type="text" id="registrlogin" name="NewUserName">
                    <label for="email">E-mail:
                        <small>please@email.me</small>
                    </label>
                    <input type="text" id="email" name="NewUserEmail">
                    <label for="registrpass">Пароль:
                        <small>english please</small>
                    </label>
                    <input type="password" id="registrpass" name="NewUserPWD">
                    <label for="registrrepass">Подтвердите пароль:
                        <small>english please</small>
                    </label>
                    <input type="password" id="registrrepass" name="NeUserREPWD">
                    <label for="firstname">Имя:
                        <small>just letters</small>
                    </label>
                    <input type="text" id="firstname" name="NewUserFirstName">
                    <label for="lastname">Фамилия:
                        <small>just letters</small>
                    </label>
                    <input type="text" id="lastname" name="NewUserLastName">
                    <!--<input class="sendform" type="submit" value="Зарегистрироваться">-->
                    <button class="sendform" type="submit">Зарегистрироваться</button>
                </form>
            </div>

            <div class="allforms" id="sendedregemailwrapper">
                <div class="popup_close" id="closesendedregemail"></div>
                <form id="sendedregemailform" action="#" method="post" name="registration" autocomplete="on">
                    <p>Спасибо за регистрацию! На Вашу почту отправлено сообщение для подтверждения регистрации.</p>
                    <!--<input type="submit" value="OK">-->
                </form>
            </div>

            <div class="allforms" id="incorrectlogpasswrapper">
                <div class="popup_close" id="closeincorrectlogpass"></div>
                <p>Вы ввели неправильное имя или пароль. Пожалуйста, введите правильные данные и попробуйте ещё раз. Не
                    забудьте, что пароль чувствителен к регистру.</p>

                <p>Забыли пароль? <a href="#">Напомним!</a></p>

                <form id="incorrectlogpassform" action="#" method="post" name="login" autocomplete="on">
                    <label for="reautorizlogin">Логин:
                        <small>or e-mail; english please</small>
                    </label>
                    <input type="text" id="reautorizlogin" name="reautorizlogin">
                    <label for="reautorizpass">Пароль:
                        <small>english please</small>
                    </label>
                    <input type="password" id="reautorizpass" name="reautorizpass">
                    <!--<input class="sendform" type="submit" value="Войти">-->
                    <button class="sendform" type="submit">Войти</button>
                </form>
            </div>

            <div class="allforms" id="rememberpasswrapper">
                <div class="popup_close" id="closerememberpass"></div>
                <form id="rememberpassform" action="#" method="post" name="registration" autocomplete="on">
                    <p>Если забыли свой пароль, Вы можете ввести свой адрес электронной почты указанный при регистрации.
                        Вам будет выслана инструкция по смене пароля.</p>
                    <label for="reemail">E-mail:
                        <small>please@email.me</small>
                    </label>
                    <input type="text" id="reemail" name="reemail">
                    <!--<input class="sendform" type="submit" value="Отправить">-->
                    <button class="sendform" type="submit">Отправить</button>
                </form>
            </div>

            <div id="mask" class="mask"></div>
            <!--END of Popups -->
            <div class="clb"></div>
        </header>

        <aside>
            <!--<a href="http://sitear.ru/material/jquery-validaciya-form-i-polej">jQuery валидация форм и полей</a><br/>
            <a title="jQuery Form Styler" href="http://dimox.name/jquery-form-styler/">jQuery Form Styler</a>-->
            <ul style="display:none">
                <li><a id="popup_1" href="#">Спасибо за регистрацию!</a></li>
                <br>
                <li><a id="popup_2" href="#">Неправильное имя или пароль...</a></li>
                <br>
                <li><a id="popup_3" href="#">Забыли пароль?</a></li>
            </ul>
            <a href="adminpage.html">ADMIN</a>
            <br/><br/><br/>
            <a href="userpage.html">USER</a>

        </aside>

        <section>
            <article></article>
            <article></article>
        </section>

        <div style="text-align: center;">
            <h2><%=request.getAttribute("Message")%>
            </h2>
            <br/>
        </div>
    </div>
    <!-- END of content -->
</div>
<!-- END of main -->

<footer>
    <p>Copyright &copy;&nbsp;2014&nbsp;DAJ Group.</p>

    <p>For more information please contact us via <a href="mailto:email@email.com">taras.daj@gmail.com</a></p>
</footer>

<!-- Scripts an CSS -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="js/jquery.min.js"></script>
<!--<script src="http://cdnjs.cloudflare.com/ajax/libs/Chart.js/0.2.0/Chart.min.js"></script>-->
<!--<script src="js/Chart.min.js"></script>-->
<script src="js/script.js"></script>
<script src="js/jquery.formstyler.min.js"></script>
<!-- END of Scripts -->

</body>
</html>