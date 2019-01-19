$("#newProcedure").submit(async function(e){
    e.preventDefault();
    var token = localStorage.getItem("token");
    var patientId = localStorage.getItem("patientId");
    var id = localStorage.getItem("resourceId");
    body = {
        procedureName:$("#procedureName").val(),
        resource:$("#resource").val(),
        patientId:patientId,
        rating:parseInt($("#rating").val()),
        notes:$("#notes").val()
    }
    var request = new Request('http://localhost:8080/api/procedure',{
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
