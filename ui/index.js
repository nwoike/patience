var express = require('express') 
var path = require('path')
var app = express()  

app.set('port', 8000);

app.use(express.static(path.join(__dirname, 'src')))

var server = app.listen(app.get('port'), function() {
  var port = server.address().port;
  console.log(`Server started on port ${port}.`);
});
