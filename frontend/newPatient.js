$('document').ready(function(){
    
   
});
$("#newPatient").submit(async function(e){
    e.preventDefault();
    
    var token = localStorage.getItem("token");
    var id = localStorage.getItem("resourceId");
    body = {
        firstName:$("#firstName").val(),
        lastName:$("#lastName").val(),
        resourceId:id,
        reasonForVisit:$("#reason").val(),
        floor:$("#floor").val()
    }
    var request = new Request('http://localhost:8080/api/patient',{
            method: 'PUT',
            body:JSON.stringify(body),
            headers: new Headers({
                'Content-Type': ' 	application/json',
                'X-Requested-With': 'XMLHttpRequest',
                'Cache-Control' : 'no-cache',
                'Access-Control-Allow-Origin':'*',
            'Authorization':'Bearer' + ' ' + token
            })
    });
    var response = await fetch(request);
    var data = await response.json();
    console.log(data);
});