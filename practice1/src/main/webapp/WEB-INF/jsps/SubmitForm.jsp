<!DOCTYPE html>
<html>
<head>
    <title>Submit Post</title>
</head>
<body>
    <h1>Submit Post</h1>
    <form id="postForm">
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required><br>
        <label for="description">Description:</label>
        <input type="text" id="description" name="description" required><br>
        <label for="content">Content:</label>
        <textarea id="content" name="content" rows="5" required></textarea><br>
        <input type="submit" value="Submit">
    </form>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script>
        // Handle form submission using Ajax
        $(document).ready(function () {
            $("#postForm").submit(function (event) {
                event.preventDefault(); // Prevent form submission

                // Collect form data
                var formData = {
                    "title": $("#title").val(),
                    "description": $("#description").val(),
                    "content": $("#content").val()
                };

                // Send data using Ajax
                $.ajax({
                    type: "POST",
                    contentType: "application/json",
                    url: "http://localhost:8080/api/post",
                    data: JSON.stringify(formData),
                    dataType: "json",
                    success: function (data) {
                        // Handle success response
                        alert("Post submitted successfully!");
                        // Optionally, you can redirect the user to another page here
                    },
                    error: function (xhr, status, error) {
                        // Handle error response
                        alert("Error submitting post: " + xhr.responseText);
                    }
                });
            });
        });
    </script>
</body>
</html>
