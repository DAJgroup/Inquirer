jQuery(function ($) {

    $('a#menu_login').click(function (e) {
        e.preventDefault();
        $('#mask').fadeIn('fast');
        $('#loginformwrapper').show();
    });
    $('a#menu_registration').click(function (e) {
        e.preventDefault();
        $('#mask').fadeIn('fast');
        $('#regformwrapper').show();
    });

    //Popups #sendedregemailwrapper, #incorrectlogpasswrapper, #rememberpasswrapper

    $('#popup_1').click(function (e) {
        e.preventDefault();
        $('#mask').fadeIn('fast');
        $('#sendedregemailwrapper').show();
    });

    $('#popup_2').click(function (e) {
        e.preventDefault();
        $('#mask').fadeIn('fast');
        $('#incorrectlogpasswrapper').show();
    });

    $('#popup_3').click(function (e) {
        e.preventDefault();
        $('#mask').fadeIn('fast');
        $('#rememberpasswrapper').show();
    });

    //END of popups #sendedregemailwrapper,	#incorrectlogpasswrapper, #rememberpasswrapper

    $('.allforms .popup_close, #mask').click(function (e) {
        e.preventDefault();
        $('#mask, .allforms, #autorizloginInfo, #reautorizloginInfo, #autorizpassInfo, #reautorizpassInfo, #registrloginInfo, #registrpassInfo, #registrrepassInfo, #emailInfo, #reemailInfo, #firstnameInfo, #lastnameInfo').hide();
        /*		$('#autorizloginInfo').hide();
         $('#autorizpassInfo').hide();
         $('#registrloginInfo').hide();
         $('#registrpassInfo').hide();
         $('#registrrepassInfo').hide();
         $('#emailInfo').hide();
         $('#firstnameInfo').hide();
         $('#lastnameInfo').hide();*/
    });

    /*	$ ('#mask').click(function(){
     $(this).hide();
     $('.allforms, #autorizloginInfo, #autorizpassInfo, #registrloginInfo, #registrpassInfo, #registrrepassInfo, #emailInfo, #firstnameInfo, #lastnameInfo').hide();
     //$('#autorizloginInfo').hide();
     //$('#autorizpassInfo').hide();
     //$('#registrloginInfo').hide();
     //$('#registrpassInfo').hide();
     //$('#registrrepassInfo').hide();
     //$('#emailInfo').hide();
     //$('#firstnameInfo').hide();
     //$('#lastnameInfo').hide();
     });*/

    $(document).ready(function () {
        var jVal = {
            'autorizlogin': function () {
                $('body').append('<div id="autorizloginInfo" class="info"></div>');
                var nameInfo = $('#autorizloginInfo');
                var ele = $('#autorizlogin');
                var pos = ele.offset();
                var autorizloginRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });

                if (ele.val().length < 5 || !autorizloginRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> Ваш логин должен быть<br> не менее 5 символов').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'reautorizlogin': function () {
                $('body').append('<div id="reautorizloginInfo" class="info"></div>');
                var nameInfo = $('#reautorizloginInfo');
                var ele = $('#reautorizlogin');
                var pos = ele.offset();
                var reautorizloginRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });

                if (ele.val().length < 5 || !reautorizloginRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> Ваш логин должен быть<br> не менее 5 символов').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'autorizpass': function () {
                $('body').append('<div id="autorizpassInfo" class="info"></div>');
                var nameInfo = $('#autorizpassInfo');
                var ele = $('#autorizpass');
                var pos = ele.offset();
                var autorizpassRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });

                if (ele.val().length < 6 || !autorizpassRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> Ваш пароль должен быть<br> не менее 6 символов').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'reautorizpass': function () {
                $('body').append('<div id="reautorizpassInfo" class="info"></div>');
                var nameInfo = $('#reautorizpassInfo');
                var ele = $('#reautorizpass');
                var pos = ele.offset();
                var reautorizpassRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });

                if (ele.val().length < 6 || !reautorizpassRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> Ваш пароль должен быть<br> не менее 6 символов').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'registrlogin': function () {
                $('body').append('<div id="registrloginInfo" class="info"></div>');
                var nameInfo = $('#registrloginInfo');
                var ele = $('#registrlogin');
                var pos = ele.offset();
                var registrloginRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });

                if (ele.val().length < 5 || !registrloginRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> Ваш логин должен быть<br> не менее 5 символов').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'registrpass': function () {
                $('body').append('<div id="registrpassInfo" class="info"></div>');
                var nameInfo = $('#registrpassInfo');
                var ele = $('#registrpass');
                var pos = ele.offset();
                var registrpassRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });

                if (ele.val().length < 6 || !registrpassRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> Ваш пароль должен быть<br> не менее 6 символов').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'registrrepass': function () {
                $('body').append('<div id="registrrepassInfo" class="info"></div>');
                var nameInfo = $('#registrrepassInfo');
                var ele = $('#registrrepass');
                var reele = $('#registrpass');
                var pos = ele.offset();
                var registrrepassRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });

                if (ele.val().length < 6 || ele.val() !== reele.val() || !registrrepassRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> Ваш пароль должен быть<br> не менее 6 символов и он<br> должен повторять первый').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'email': function () {
                $('body').append('<div id="emailInfo" class="info"></div>');
                var nameInfo = $('#emailInfo');
                var ele = $('#email');
                var pos = ele.offset();
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });
                //var emailRegex = /^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.{}]{2,6})$/gi; был заменен, something@gmail.......com "проходит"
                var emailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/gi;
                if (!emailRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> введите корректный e-mail').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'reemail': function () {
                $('body').append('<div id="reemailInfo" class="info"></div>');
                var nameInfo = $('#reemailInfo');
                var ele = $('#reemail');
                var pos = ele.offset();
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });
                //var reemailRegex = /^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.{}]{2,6})$/gi; был заменен, something@gmail.......com "проходит"
                var reemailRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/gi;
                if (!reemailRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> введите корректный e-mail').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'firstname': function () {
                $('body').append('<div id="firstnameInfo" class="info"></div>');
                var nameInfo = $('#firstnameInfo');
                var ele = $('#firstname');
                var pos = ele.offset();
                var firstnameRegex = /^[a-zA-Zа-яА-ЯёЁ_ -`'][^0-9]+$/gi;
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });

                if (ele.val().length < 2 || !firstnameRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> Ваше имя должно быть не менее<br> 2 символов, без цифр').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'lastname': function () {
                $('body').append('<div id="lastnameInfo" class="info"></div>');
                var nameInfo = $('#lastnameInfo');
                var ele = $('#lastname');
                var pos = ele.offset();
                var lastnameRegex = /^[a-zA-Zа-яА-ЯёЁ_ -`'][^0-9]+$/gi;
                nameInfo.css({
                    top: pos.top - 3,
                    left: pos.left + ele.width() + 15
                });
                if (ele.val().length < 2 || !lastnameRegex.test(ele.val())) {
                    jVal.errors = true;
                    nameInfo.removeClass('correct').addClass('error').html('<i class="fa fa-arrow-left"></i> Ваша фамилия должна быть не<br> менее 2 символов, без цифр').show();
                    ele.removeClass('normal').addClass('wrong');
                } else {
                    nameInfo.removeClass('error').addClass('correct').html('<i class="fa fa-check fa-lg"></i>').show();
                    ele.removeClass('wrong').addClass('normal');
                }
            },

            'sendIt': function () {
                if (!jVal.errors) {
                    $('#loginform').submit();
                }
            }
        };

        $('.sendform').click(function () {
            var obj = $.browser.webkit ? $('body') : $('html');
            obj.animate({scrollTop: $('#loginform').offset().top}, 750, function () {
                jVal.errors = false;
                jVal.autorizlogin();
                jVal.reautorizlogin();
                jVal.autorizpass();
                jVal.reautorizpass();
                jVal.registrlogin();
                jVal.registrpass();
                jVal.registrrepass();
                jVal.email();
                jVal.reemail();
                jVal.firstname();
                jVal.lastname();
                jVal.sendIt();
            });
            return false;
        });

        $('#autorizlogin').change(jVal.autorizlogin);
        $('#reautorizlogin').change(jVal.reautorizlogin);
        $('#autorizpass').change(jVal.autorizpass);
        $('#reautorizpass').change(jVal.reautorizpass);
        $('#registrlogin').change(jVal.registrlogin);
        $('#registrpass').change(jVal.registrpass);
        $('#registrrepass').change(jVal.registrrepass);
        $('#email').change(jVal.email);
        $('#reemail').change(jVal.reemail);
        $('#firstname').change(jVal.firstname);
        $('#lastname').change(jVal.lastname);
    });

    $('.arrowopen').click(function (e) {
        e.preventDefault();
        $(this).toggleClass('arrowclose');
        $(this).next().slideToggle(0);
        //$(this).parent().next().slideToggle( 0 );
    });

});

(function ($) {
    $(function () {

        $("input, select, text, textarea").styler();

    });
})(jQuery);