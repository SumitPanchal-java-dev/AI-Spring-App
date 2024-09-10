document.getElementById('recipeForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const ingredients = document.getElementById('ingredients').value;
    const cuisine = document.getElementById('cuisine').value;
    const dietaryRestrictions = document.getElementById('dietaryRestrictions').value;

    fetch(`/recipe-creator?ingrdients=${ingredients}&cuisin=${cuisine}&dietryrestriction=${dietaryRestrictions}`)
        .then(response => response.text())
        .then(data => {
            document.getElementById('recipeResult').innerHTML = `<h2>Generated Recipe</h2><pre>${data}</pre>`;
        })
        .catch(error => console.error('Error:', error));
});
