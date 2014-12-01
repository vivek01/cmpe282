function signCheck(){
	$.post("http://ec2-54-172-52-250.compute-1.amazonaws.com:8080/CloudServices/rest/users/signup",
			formToJSON(),
			function(data,status){
	    alert("Data: " + data + "\nStatus: " + status);
	  }
	);
}

function formToJSON() {
    return JSON.stringify({
    "firstName": $('#firstName').val(),
    "lastName": $('#lastName').val(),
    "email": $('#email').val(),
    "passwd": $('#passwd').val(),
    });
}