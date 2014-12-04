jQuery(function($){

	// alert("hello");

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
	}); 
	$('#mask').click(function(){
		$(this).hide();
		$('.allforms').hide();
		$('#emailInfo').hide();
		$('#firstnameInfo').hide();
		$('#lastnameInfo').hide();
	});

	$(document).ready(function(){
		var jVal = {
			'email' : function(){
			$('body').append('<div id="emailInfo" class="info"></div>');
			var nameInfo = $('#emailInfo');
			var ele = $('#email');
			var pos = ele.offset();
			nameInfo.css({
				top: pos.top-3,
				left: pos.left+ele.width()+15
			});
			//var emailRegex = /^.+@.+[.].{2,}$/i;
			var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$/i;
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
				nameInfo.css({
					top: pos.top-3,
					left: pos.left+ele.width()+15
				});
				if(ele.val().length < 2){
					jVal.errors = true;
					nameInfo.removeClass('correct').addClass('error').html('&lt; менее двух символов').show();
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
				nameInfo.css({
					top: pos.top-3,
					left: pos.left+ele.width()+15
				});
				if(ele.val().length < 2){
					jVal.errors = true;
					nameInfo.removeClass('correct').addClass('error').html('&lt; менее двух символов').show();
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
				jVal.email();
				jVal.firstname();
				jVal.lastname();
				jVal.sendIt();
			});
			return false;
		});
		$('#email').change(jVal.email);
		$('#firstname').change(jVal.firstname);
		$('#lastname').change(jVal.lastname);
	});

});

(function($) {
	$(function() {

		$('input, select, text, textarea').styler();

	});
})(jQuery);