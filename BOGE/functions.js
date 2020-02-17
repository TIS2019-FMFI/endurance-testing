var meno = "http://localhost:8080/TesticekNatentoTyzdnicek/rest/data/"
var logData;
var umeno;
var heslo;
var subDataVerzie = [];
var actualRow;

function nacitaj(){ //nacitava data z allProdkt
	var xmlhttp = new XMLHttpRequest();
	var url = meno + "allprodukt";
	var data = {FromDate: document.getElementById("datumOd").value,
				ToDate: document.getElementById("datumDo").value,
				Zeichnungsnummer: document.getElementById("cisloDielu").value,
				Kunde: document.getElementById("zakaznik").value,
				Bezeichnung: document.getElementById("umiestnenieDielu").value,
				zostava: document.getElementById("cisloZostavy").value, 
				nrNumber: document.getElementById("cisloObjednavky").value};
				
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			 var data2 = JSON.parse(this.responseText);
			 vypis(data2);
		}
	}
	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	var tmp = "";
	for (var key in data) {
		if (data.hasOwnProperty(key)) {
			tmp += key + "=" + data[key] + "&";
		}
	}
	tmp = tmp.substring(0,tmp.length-2);
	xmlhttp.send(tmp);
	return false;
}

function log(){ // pripojenie sa na log a stiahnutie údajov
	var xmlhttp = new XMLHttpRequest();
	var url = meno + "log";
				
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var data2 = JSON.parse(this.responseText);
			logData = data2;
			vypisLog(data2);
		}
	}
	xmlhttp.open("GET", url, true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlhttp.send(null);
	return false;
}

function login(){ //login nie ? :D 
	var xmlhttp = new XMLHttpRequest();
	var url = meno + "user";
				
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var data2 = JSON.parse(this.responseText);
			if (data2 == true){
				window.open("main-screen.html","_self")
			}
			else {
				window.alert("Zlé prihlasovacie meno alebo heslo");
			}
		}
	}
	umeno = document.getElementById("uname").value;
	heslo = document.getElementById("psw").value;
	console.log(umeno + " " + heslo);
	
	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	var tmp = "uname=" + document.getElementById("uname").value + "&pass=" + document.getElementById("psw").value;
	xmlhttp.send(tmp);
	return false;
}

function newProduct(){ //vytvorenie nového produktu
	var xmlhttp = new XMLHttpRequest();
	var url = meno + "newProdukt";
	var data = {Bezeichnung: document.getElementById("umiestnenieGet").value,
				Kunde: document.getElementById("zakaznikGet").value,
				Zeichnungsnummer: document.getElementById("cisloVykresuGet").value,	
				Cislo: document.getElementById("cisloObjedavkyGet").value,
				Zostava: document.getElementById("zostavaGet").value, 
				User: "admin"};
				
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			window.alert(this.responseText);
		}
	}
	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	var tmp = "";
	for (var key in data) {
		if (data.hasOwnProperty(key)) {
			tmp += key + "=" + data[key] + "&";
		}
	}
	tmp = tmp.substring(0,tmp.length-2);
	xmlhttp.send(tmp);
	return true;
}

function fillData(){ //prečítanie Excelu a vyplnenie polí
	let excel = document.getElementById("excel").files[0];  // file from input
	let xmlhttp = new XMLHttpRequest();
	let formData = new FormData();
	
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var data2 = JSON.parse(this.responseText);
			document.getElementById("umiestnenieGet").value = data2["umiestnenieGet"];
			document.getElementById("zakaznikGet").value = data2["zakaznikGet"];
			document.getElementById("cisloVykresuGet").value = data2["cisloVykresuGet"];
			document.getElementById("cisloObjedavkyGet").value = data2["cisloObjedavkyGet"];
			document.getElementById("zostavaGet").value = data2["zostavaGet"];
		}
	}
	formData.append("file", excel);                                
	xmlhttp.open("POST", meno+"Excel");
	xmlhttp.send(formData);
	return false;
}

function download() { //sťahovanie logu
	var exportData = "";
	var today = new Date();
	var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
	var today = new Date();
	var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
	
	for (var i = 0; i < logData.length; i++){
		for (var key in logData[i]){
			exportData += logData[i][key] + " ";
		}
		exportData += "\n";
	}
	
    var file = new Blob([exportData], {type: "text/plain"});
    if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(file, "log " + date + " " + time + ".txt");
    else { // Others
        var a = document.createElement("a"),
                url = URL.createObjectURL(file);
        a.href = url;
        a.download = "log " + date + " " + time + ".txt";
        document.body.appendChild(a);
        a.click();
        setTimeout(function() {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);  
        }, 0); 
    }
	return false;
}

function setDatum(id, a){ //nastavenie dátum	
	let xmlhttp = new XMLHttpRequest();
	let formData = new FormData();
	
	var tmp = "datum="+a.toString()+"&uname=admin&verzia="+id;
	formData.append("datum", a); 
	formData.append("uname", "admin");  
	formData.append("verzia", id);  	
	xmlhttp.open("POST", meno+"novyDatum", true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlhttp.send(tmp);
	return false;
}

function setZostava(id, a){ //nastavenie zostavy	
	let xmlhttp = new XMLHttpRequest();
	let formData = new FormData();
	
	/*xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var data2 = JSON.parse(this.responseText);
		}
	}   */
	var k = id.substring(0,4) + "___" + id.substring(7,id.length);
	
	var tmp = "zostava="+ a +"&uname=admin&zeichnungsnummer=" + k;	
	xmlhttp.open("POST", meno + "nastavZostavu", true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlhttp.send(tmp);
	return false;
}

function nacitajVerzie(zeichN){ //nacitava verzie
	var xmlhttp = new XMLHttpRequest();
	var url = meno + "verzie";
	zeichN = "111.___.333.444";	
	
	xmlhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			 var data2 = JSON.parse(this.responseText);
			 subDataVerzie = data2;
		}
	}
	xmlhttp.open("POST", url, true);
	xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xmlhttp.send("zeich=" + zeichN);
	return false;
}

function showSetters(){
	document.getElementById("settery").style.display= "block";
	return false;
}

function hideSetters(){
	if (document.getElementById("newDate").value != "" && document.getElementById("newZostava").value != ""){
		setDatum(actualRow, document.getElementById("newDate").value);
		setZostava(actualRow, document.getElementById("newZostava").value)		
	}
	else if (document.getElementById("newDate").value != ""){
		setDatum(actualRow, document.getElementById("newDate").value);		
	}
	else if (document.getElementById("newZostava").value != "" ){
		setZostava(actualRow, document.getElementById("newZostava").value)
	}
	
	document.getElementById("settery").style.display = "none";
	return false;
}

function setActualRow(row) {
	actualRow = row;
}

function vypisLog(data){ //vypisanie logu
	var tab = document.getElementById("tableBody");
	tab.innerHTML = '';
	var riadok;
	var bunka;
	var text;
	
	for (var i  = 0; i < data.length; i++){
		var diel = data[i];
		//var kontakty=diel["kontakty"];

		riadok=document.createElement("tr");

		bunka=document.createElement("td");
		text=document.createTextNode(diel["user"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		bunka=document.createElement("td");
		text=document.createTextNode(diel["updated_what"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		bunka=document.createElement("td");
		text=document.createTextNode(diel["updated_how"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		bunka=document.createElement("td");
		text=document.createTextNode(diel["updated_at"]);
		bunka.appendChild(text);
		riadok.appendChild(bunka);

		tab.appendChild(riadok);
	}
}

