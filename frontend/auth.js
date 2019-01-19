
$("#login").submit(function(e){
  e.preventDefault();
  console.log("here");
  Authorize($("#username").val(),$("#password").val());
});
$("#pos").click(function(){
    CheckPosition();
});



function Authorize(user,pass){
    var request = new Request('http://localhost:8080/api/auth/signin',{
             method: 'POST',
             body: JSON.stringify({"username":user,"password":pass}),
             headers: new Headers({
                 'Content-Type': ' 	application/json',
                 'X-Requested-With': 'XMLHttpRequest',
                 'Cache-Control' : 'no-cache',
                 'Access-Control-Allow-Origin':'*'
             })
         });
            fetch(request).then( function(resp){
                resp.json().then(function(data) {
                  localStorage.setItem("token", data.response.token);
                  localStorage.setItem("resourceId",data.response.resourceId);
                    window.location.href = "landingResource.html";
                  
                });
            }).catch(err =>{
            console.log(err);
            });
}



