function LogInfun(){

	var URL = "http://ec2-54-172-52-250.compute-1.amazonaws.com:8080/CloudServices/rest/users/signin";
	
	alert("signin button clicked");//+formToJSON());
	
	$.ajax({
			type: "POST",
			url: URL,
			contentType: "application/json",
			dataType: 'json',
			redirect: true,
			data : formToJSON(),
			
				//success: function () { //success(data); }
			success: function(redir, textStatus, jqXHR){
					alert("you are logged in");
					window.location.href="http://ec2-54-172-52-250.compute-1.amazonaws.com:8080/CloudServices/rest/users/home";
				},
			error: function(textStatus, jqXHR,errorThrown){
				alert(textStatus+" "+jqXHR);
			}

		});
}

function formToJSON() {
    return JSON.stringify({
    "email": $('#userName').val(),
    "passwd": $('#passwd').val(),
    });
}