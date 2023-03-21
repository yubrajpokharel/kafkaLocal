<!DOCTYPE html>
<html>
<head>
    <title>Kafka Topics</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js" integrity="sha384-mQ93GR66B00ZXjt0YO5KlohRA5SY2XofN4zfuZxLkoj1gXtW8ANNCe9d5Y3eG5eD" crossorigin="anonymous"></script>
    <script>
        $(function() {
            // Get the list of topics and display them in a table
            $.get("/api/topics", function(topics) {
                var table = $("<table>").addClass("table");
                $.each(topics, function(index, topic) {
                    var row = $("<tr>");
                    $("<td>").append($("<a>").text(topic).attr("href", "/topic/" + topic)).appendTo(row);

                  row.appendTo(table);
                });
                $("#topics").html(table);
            });

            // Handle form submission to create a new topic
            $("#newTopicForm").submit(function(event) {
                event.preventDefault();
                var newTopic = $("#newTopicName").val();
                $.ajax({
                    url: "/api/topics",
                    type: "POST",
                    data: JSON.stringify({ "name": newTopic }),
                    contentType: "application/json",
                    success: function(result) {
                        // Display a success message and refresh the list of topics
                        $("#newTopicResult").html("<div class='alert alert-success'>Topic created successfully</div>");
                        $.get("/api/topics", function(topics) {
                            var table = $("<table>").addClass("table");
                            $.each(topics, function(index, topic) {
                                var row = $("<tr>");
                              $("<td>").append($("<a>").text(topic).attr("href", "/topic/" + topic)).appendTo(row);
                               row.appendTo(table);
                            });
                            $("#topics").html(table);
                        });
                    },
                    error: function(xhr, status, error) {
                        // Display an error message
                        $("#newTopicResult").html("<div class='alert alert-danger'>Error creating topic: " + error + "</div>");
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="container mt-3">
    <h1>Kafka Topics</h1>
    <div id="topics"></div>
    <h2>Create a new topic</h2>
    <form id="newTopicForm">
        <div class="form-group">
            <label for="newTopicName">Topic name:</label>
            <input type="text" class="form-control" id="newTopicName" name="newTopicName">
        </div>
        <br>
        <button type="submit" class="btn btn-primary">Create topic</button>
    </form>
    <br>
    <div id="newTopicResult"></div>
</div>
</body>
</html>