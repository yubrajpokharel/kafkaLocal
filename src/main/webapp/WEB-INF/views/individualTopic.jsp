<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Kafka Messages</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-GLhlTQ8iRABdZLl6O3oVMWSktQOp6b7In1Zl3/Jr59b6EGGoI1aFkw7cmDA6j6gD" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1>Kafka Messages</h1>
    <div class="row">
        <div class="col-md-12">
            <h3>Send message to topic</h3>
            <form id="sendMessageForm">
                <div class="form-group">
                    <label for="topicNameInput">Topic name</label>
                    <input type="text" class="form-control" id="topicNameInput" name="topic" value="${topicName}" disabled>
                </div>
                <div class="form-group">
                    <label for="messageInput">Message</label>
                    <textarea class="form-control" id="messageInput" name="message"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Send message</button>
            </form>
            <br>
        </div>
        <div class="col-md-12">
            <div id="sendMessageResult"></div>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.min.js" integrity="sha384-mQ93GR66B00ZXjt0YO5KlohRA5SY2XofN4zfuZxLkoj1gXtW8ANNCe9d5Y3eG5eD" crossorigin="anonymous"></script>
<script>
  $(document).ready(function() {
    // Handle the send message form submission
    $("#sendMessageForm").submit(function(event) {
      event.preventDefault();

      // Send a POST request to the API endpoint to send the message
      $.ajax({
        url: "/api/topics/message/send",
        type: "POST",
        data: {
          topic: $("#topicNameInput").val(),
          message: $("#messageInput").val()
        },
        success: function(result) {
          // Display a success message
          $("#sendMessageResult").html("<div class='alert alert-success'>Message sent successfully</div>");
        },
        error: function(xhr, status, error) {
          // Display an error message
          $("#sendMessageResult").html("<div class='alert alert-danger'>Error sending message: " + error + "</div>");
        }
      });
    });
  });
</script>


