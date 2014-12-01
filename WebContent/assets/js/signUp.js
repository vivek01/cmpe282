function signUpfun(){
	
alert('user  '+formToJSON());	

jQuery.ajax({
    type: "POST",
    url: "http://localhost:8080/CloudServices/rest/users/signup",
    data: formToJSON(),
    contentType: "application/json; charset=utf-8",
    dataType: "json",
    success: function (data, status, jqXHR) {
    	var msg=data.message; 
    	if(msg=="Successfully signed")
    		{
    		alert("Successfully created sign up");
    		window.location.replace("http://localhost:8080/CloudServices/view/SignIn.html");
    		}
    	else{
    		alert("unable to sign up");
    	}
    	
    },

    error: function (jqXHR, status) {            
    	alert(jqXHR+" "+status);
    }

});
}

function formToJSON() {
    return JSON.stringify({
    "firstName": $('#firstName').val(),
    "lastName": $('#lastName').val(),
    "email": $('#email').val(),
    "passwd": $('#passwd').val(),
    });
}