async function getProcedures(){
    var token = localStorage.getItem("token");
    var id = localStorage.getItem("resourceId");
    var request = new Request('http://localhost:8080/api/resource/'+id,{
            method: 'GET',
            headers: new Headers({
            'Content-Type': '   application/json',
                'X-Requested-With': 'XMLHttpRequest',
                'Cache-Control' : 'no-cache',
                'Access-Control-Allow-Origin':'*',
            'Authorization':'Bearer' + ' ' + token
            })
    });
    var response = await fetch(request);
    var data = await response.json();
    if(data.message="success"){

        for(var i= 0; i < data.response.length;i++){
            var patientId = data.response[i].patientId;
            var patientName = data.response[i].patientName;
            var procedureName = data.response[i].procedureName;
            var timeRequested = data.response[i].timeRequested;
            var floor = data.response[i].floor;
            var markup = "<tr><td>" + patientName + "</td><td>" + procedureName +"</td><td>" + timeRequested + "</td><td>" + floor + "</td><td class='center'><Button id="+patientId+" class='profiles btn btn-success'>Profile</Button></td></tr>";
            $("#proceduresBody").append(markup);
        }
        $(".profiles").click(function(event){
            localStorage.setItem("patientId", event.target.id);
            window.location.href = "patientProfile.html";
        });
    }
    else{
        alert("error");
    }
}
$( document ).ready(function(){
     getProcedures();
     
     
});


$("#newPatient").click(function(event){
    window.location.href = "newPatient.html";
    
});
