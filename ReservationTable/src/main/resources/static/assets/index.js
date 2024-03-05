// 1. what is API
// 2. How do I call API
// 3. Explain code
const host = "https://vapi.vnappmob.com/api/";
var callAPI = (api) => {
    return axios.get(api)
        .then((response) => {
            renderDataP(response.data.results, "province");
           
        });
}
callAPI('https://vapi.vnappmob.com/api/province');
var callApiDistrict = (api) => {
    return axios.get(api)
        .then((response) => {
            renderDataD(response.data.results, "district");
             
        });
}
var callApiWard = (api) => {
    return axios.get(api)
        .then((response) => {
            renderDataW(response.data.results, "ward");
            
        });
}

var renderDataD = (array, select) => {
   let row = ' <option disable value="">Select District</option>';
    array.forEach(element => {
        row += `<option value="${element.district_id}" th:value="${element.district_name}">${element.district_name}</option>`
    });
    document.querySelector("#" + "district1").innerHTML = row
}
var renderDataP = (array, select) => {
     let row = ' <option disable value="">Select Provice</option>';
    array.forEach(element => {
        row += `<option value="${element.province_id}" th:value="${element.province_name}">${element.province_name}</option>`
    });
    document.querySelector("#" + "province").innerHTML = row
 }
var renderDataW= (array, select) => {
 let row = ' <option disable value="">Select Ward</option>';
    array.forEach(element => {
        row += `<option value="${element.ward_id}" th:value="${element.ward_name}">${element.ward_name}</option>`
    });
    document.querySelector("#" + "ward1").innerHTML = row
}
$("#province").change(() => {
    callApiDistrict(host + "province/district/" + $("#province").val());
    printResult();
});
$("#district1").change(() => {
    callApiWard(host + "province/ward/" + $("#district1").val());
    printResult();
});
$("#ward1").change(() => {
    printResult();
})

var printResult = () => {
    if ($("#district1").val() != "" && $("#province").val() != "" &&
        $("#ward1").val() != "") {
        let result = $("#province option:selected").text() +
            " | " + $("#district1 option:selected").text() + " | " +
            $("#ward1 option:selected").text();
        $("#result").text(result)
           var p = $("#province option:selected").text()
        var d = $("#district1 option:selected").text()
        var w =  $("#ward1 option:selected").text()
        const inputP = document.getElementById("city");
        inputP.value = p
        const inputD = document.getElementById("district");
        inputD.value = d
     const inputW = document.getElementById("ward");
        inputW.value = w
        
    }

}