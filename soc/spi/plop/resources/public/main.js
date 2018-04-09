
var sock = new WebSocket("ws://" + location.host + "/ws");

function addAudioFile(file) {
    var audioplayer =
    '<div class="card-text row">' +
        '<div class="col-12">' +
          '<label><small>' + file +'</small></label>' +

    '<audio controls="controls" style="width:100%">' +
      '<source src="' + file + '" type="audio/wav" />' +
      '</div></div>';
    $( "#record-filelist" ).append( audioplayer );
}

sock.onmessage = function(e) {

  msg = JSON.parse(e.data);


  console.log(msg.id);
  console.log(msg.chan);
  console.log(msg.display);


  if (msg.chan > 0) {
    $('#' + msg.id).val(msg.display);
  }

  if (msg.id == "recording") {
    
    if(msg.numid == 1) { //starting recording
      $('#recording').prop('checked', true);
      $( "#record-log" ).append( '<div class="card-text row"><div class="col-12"><small>Recording to ' + msg.display + "</small></div></div>" );
    }
    else if(msg.numid == 0) { //done recording
      $('#recording').prop('checked', false);
      $( "#record-log" ).append( '<div class="card-text row"><div class="col-12"><small>Done recording to ' + msg.display + "</small></div></div>" );
      addAudioFile(msg.display);
    }
  }

  if (msg.id == "wav") {
    addAudioFile(msg.display);
  }

  if (msg.id == "streaming") {
    if(msg.numid == 1) {
      $('#streaming').prop('checked', true);
      $("#streamplatform").attr('disabled',true);
      $("#streamkey").attr('disabled',true);
    }
    else if(msg.numid == 0) {
      $('#streaming').prop('checked', false);
      $("#streamplatform").attr('disabled',false);
      $("#streamkey").attr('disabled',false);
    }
  }

}

$('input.form-check-input').change(function() {
  var checked;
  if (this.checked) {
  checked = 1; 
  }
  else {
  checked = 0; 
  }
  sock.send(JSON.stringify({numid: Number(checked), id: this.id, val: $('#streamkey').val(), chan: Number(0), platform: Number($( "#streamplatform option:selected" ).val())}));
  console.log(this.value);
});

$('input.slider').change(function() {
  sock.send(JSON.stringify({numid: Number(this.dataset.id), id: this.id, val: Number(this.value), chan: Number(this.dataset.channel), platform: 0}));
  console.log(this.value);
});
