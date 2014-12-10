jQuery(function($){

	$('a#menu_login').click(function(e){
		e.preventDefault();
		$('#mask').fadeIn('fast');
		$('#loginformwrapper').show();
	});
	$('a#menu_registration').click(function(e){
		e.preventDefault();
		$('#mask').fadeIn('fast');
		$('#regformwrapper').show();
	});
	$('.allforms .popup_close').click(function(e){
		e.preventDefault();
		$('#mask, .allforms').hide();
		$('#autorizloginInfo').hide();
		$('#autorizpassInfo').hide();
		$('#registrloginInfo').hide();
		$('#registrpassInfo').hide();
		$('#registrrepassInfo').hide();
		$('#emailInfo').hide();
		$('#firstnameInfo').hide();
		$('#lastnameInfo').hide();
	});
	$('#mask').click(function(){
		$(this).hide();
		$('.allforms').hide();
		$('#autorizloginInfo').hide();
		$('#autorizpassInfo').hide();
		$('#registrloginInfo').hide();
		$('#registrpassInfo').hide();
		$('#registrrepassInfo').hide();
		$('#emailInfo').hide();
		$('#firstnameInfo').hide();
		$('#lastnameInfo').hide();
	});

	$(document).ready(function(){
		var jVal = {
			'autorizlogin' : function(){
				$('body').append('<div id="autorizloginInfo" class="info"></div>');
				var nameInfo = $('#autorizloginInfo');
				var ele = $('#autorizlogin');
				var pos = ele.offset();
				var autorizloginRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
				nameInfo.css({
					top: pos.top-3,
					left: pos.left+ele.width()+15
				});

				if(ele.val().length < 6 || !autorizloginRegex.test(ele.val())){
					jVal.errors = true;
					nameInfo.removeClass('correct').addClass('error').html('&lt; Ваш логин должен быть<br> не менее 6 символов').show();
					ele.removeClass('normal').addClass('wrong');
				}else{
					nameInfo.removeClass('error').addClass('correct').html('√').show();
					ele.removeClass('wrong').addClass('normal');
				}
			},

			'autorizpass' : function(){
				$('body').append('<div id="autorizpassInfo" class="info"></div>');
				var nameInfo = $('#autorizpassInfo');
				var ele = $('#autorizpass');
				var pos = ele.offset();
				var autorizpassRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
				nameInfo.css({
					top: pos.top-3,
					left: pos.left+ele.width()+15
				});

				if(ele.val().length < 6 || !autorizpassRegex.test(ele.val())){
					jVal.errors = true;
					nameInfo.removeClass('correct').addClass('error').html('&lt; Ваш пароль должен быть<br> не менее 6 символов').show();
					ele.removeClass('normal').addClass('wrong');
				}else{
					nameInfo.removeClass('error').addClass('correct').html('√').show();
					ele.removeClass('wrong').addClass('normal');
				}
			},

			'registrlogin' : function(){
				$('body').append('<div id="registrloginInfo" class="info"></div>');
				var nameInfo = $('#registrloginInfo');
				var ele = $('#registrlogin');
				var pos = ele.offset();
				var registrloginRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
				nameInfo.css({
					top: pos.top-3,
					left: pos.left+ele.width()+15
				});

				if(ele.val().length < 6 || !registrloginRegex.test(ele.val())){
					jVal.errors = true;
					nameInfo.removeClass('correct').addClass('error').html('&lt; Ваш логин должен быть<br> не менее 6 символов').show();
					ele.removeClass('normal').addClass('wrong');
				}else{
					nameInfo.removeClass('error').addClass('correct').html('√').show();
					ele.removeClass('wrong').addClass('normal');
				}
			},

			'registrpass' : function(){
				$('body').append('<div id="registrpassInfo" class="info"></div>');
				var nameInfo = $('#registrpassInfo');
				var ele = $('#registrpass');
				var pos = ele.offset();
				var registrpassRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
				nameInfo.css({
					top: pos.top-3,
					left: pos.left+ele.width()+15
				});

				if(ele.val().length < 6 || !registrpassRegex.test(ele.val())){
					jVal.errors = true;
					nameInfo.removeClass('correct').addClass('error').html('&lt; Ваш пароль должен быть<br> не менее 6 символов').show();
					ele.removeClass('normal').addClass('wrong');
				}else{
					nameInfo.removeClass('error').addClass('correct').html('√').show();
					ele.removeClass('wrong').addClass('normal');
				}
			},

			'registrrepass' : function(){
				$('body').append('<div id="registrrepassInfo" class="info"></div>');
				var nameInfo = $('#registrrepassInfo');
				var ele = $('#registrrepass');
				var pos = ele.offset();
				var registrrepassRegex = /^[a-zA-Z0-9_ +-`'*]+$/gi;
				nameInfo.css({
					top: pos.top-3,
					left: pos.left+ele.width()+15
				});

				if(ele.val().length < 6 || !registrrepassRegex.test(ele.val())){
					jVal.errors = true;
					nameInfo.removeClass('correct').addClass('error').html('&lt; Ваш пароль должен быть<br> не менее 6 символов').show();
					ele.removeClass('normal').addClass('wrong');
				}else{
					nameInfo.removeClass('error').addClass('correct').html('√').show();
					ele.removeClass('wrong').addClass('normal');
				}
			},

			'email' : function(){
				$('body').append('<div id="emailInfo" class="info"></div>');
				var nameInfo = $('#emailInfo');
				var ele = $('#email');
				var pos = ele.offset();
				nameInfo.css({
					top: pos.top-3,
					left: pos.left+ele.width()+15
				});
				var emailRegex = /^([a-z0-9_\.-]+)@([a-z0-9_\.-]+)\.([a-z\.]{2,6})$/gi;
				if(!emailRegex.test(ele.val())){
					jVal.errors = true;
					nameInfo.removeClass('correct').addClass('error').html('&lt; введите корректный e-mail').show();
					ele.removeClass('normal').addClass('wrong');
				}else{
					nameInfo.removeClass('error').addClass('correct').html('√').show();
					ele.removeClass('wrong').addClass('normal');
				}
			},

			'firstname' : function(){
				$('body').append('<div id="firstnameInfo" class="info"></div>');
				var nameInfo = $('#firstnameInfo');
				var ele = $('#firstname');
				var pos = ele.offset();
				var firstnameRegex = /^[a-zA-Zа-яА-ЯёЁ_ -`'][^0-9]+$/gi;
				nameInfo.css({
					top: pos.top-3,
					left: pos.left+ele.width()+15
				});

				if(ele.val().length < 2 || !firstnameRegex.test(ele.val())){
					jVal.errors = true;
					nameInfo.removeClass('correct').addClass('error').html('&lt; Ваше имя должно быть не менее<br> 2 символов, без цифр').show();
					ele.removeClass('normal').addClass('wrong');
				}else{
					nameInfo.removeClass('error').addClass('correct').html('√').show();
					ele.removeClass('wrong').addClass('normal');
				}
			},

			'lastname' : function(){
				$('body').append('<div id="lastnameInfo" class="info"></div>');
				var nameInfo = $('#lastnameInfo');
				var ele = $('#lastname');
				var pos = ele.offset();
				var lastnameRegex = /^[a-zA-Zа-яА-ЯёЁ_ -`'][^0-9]+$/gi;
				nameInfo.css({
					top: pos.top-3,
					left: pos.left+ele.width()+15
				});
				if(ele.val().length < 2 || !lastnameRegex.test(ele.val())){
					jVal.errors = true;
					nameInfo.removeClass('correct').addClass('error').html('&lt; Ваша фамилия должна быть не менее<br> 2 символов, без цифр').show();
					ele.removeClass('normal').addClass('wrong');
				}else{
					nameInfo.removeClass('error').addClass('correct').html('√').show();
					ele.removeClass('wrong').addClass('normal');
				}
			},

			'sendIt' : function (){
				if(!jVal.errors) {
					$('#regform').submit();
				}
			}
		};

		$('#send').click(function (){
			var obj = $.browser.webkit ? $('body') : $('html');
			obj.animate({ scrollTop: $('#regform').offset().top }, 750, function (){
				jVal.errors = false;
				jVal.autorizlogin();
				jVal.autorizpass();
				jVal.registrlogin();
				jVal.registrpass();
				jVal.registrrepass();
				jVal.email();
				jVal.firstname();
				jVal.lastname();
				jVal.sendIt();
			});
			return false;
		});

		$('#autorizlogin').change(jVal.autorizlogin);
		$('#autorizpass').change(jVal.autorizpass);
		$('#registrlogin').change(jVal.autorizlogin);
		$('#registrpass').change(jVal.registrpass);
		$('#registrrepass').change(jVal.registrrepass);
		$('#email').change(jVal.email);
		$('#firstname').change(jVal.firstname);
		$('#lastname').change(jVal.lastname);
	});

});

(function($) {
	$(function() {

		$("input, select, text, textarea").styler();

	});
})(jQuery);