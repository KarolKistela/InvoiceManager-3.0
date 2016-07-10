<main>
    <article>
        <div class="card">
            <H5 class="center" style="padding: 50px 0 20px 0">
                ${sqlQuery}
            </H5>
            <div class="row center">
                ${commentON}<a href="/OpenFolder/${csvFilePathURL}" onClick="saveCSV=window.open('/OpenFolder/${csvFilePathURL}','saveCSV','width=640,height=480');
                        setTimeout(function () { saveCSV.close();}, 500);
                        return false;">
                    <p style="padding-bottom: 20px">${csvFilePath}</p>
                </a>${commentOFF}
            </div>
        </div>
    </article>
</main>