/*
 * Rufe den Server asynchron auf.
 */
function countZahl(){
    // Siehe: https://developer.mozilla.org/de/docs/Web/API/Fetch_API/Using_Fetch
    fetch('/my/count',{method:'POST'})
        .then((response) => {
            return response.text();
        })
        .then((myZahl) => {
            console.log(myZahl);
            document.getElementById("zahl").innerText = myZahl;
        });
}