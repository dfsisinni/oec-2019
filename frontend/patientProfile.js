$( document ).ready(function(){
    getProfile();
    
});

async function getProfile(){
    var token = localStorage.getItem("token");
    var id = localStorage.getItem("patientId");

    var request = new Request('http://localhost:8080/api/patient/'+id,{
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
        var patientId = data.patientId;
        var firstName = data.firstName;
        var lastName = data.lastName;
        var reason = data.reasonForVisit;
        var admissionTime = data.admissionTime;
        var lastAssessment = data.lastAssessmentTime;

        var floor = data.floor;
        procedures = data.procedureResponses;
        $("#fullName").html("Name: "+ firstName + " " + lastName);
        $("#reason").html("Reason for visit: "+ reason);
        $("#floor").html("Floor: "+ floor);
        $("#admissionTime").html("Admitted: " + admissionTime);
        $("#lastAssessment").html("Last Assessed" + lastAssessment);
        for(var i= 0; i < procedures.length;i++){
            var markup = "<tr><td>" + procedures[i].procedureName +"</td><td>" + procedures[i].timeRequested + "</td>"+"<td>" + procedures[i].notes + "</td>" + "<td>" + procedures[i].doctor + "</td>" + "<td>" + procedures[i].status + "</td>";
            $("#proceduresBody").append(markup);
        }
    }
    else{
        alert("error");
    }
}
$("#newProcedure").click(function(event){
    window.location.href = "newProcedure.html";
});