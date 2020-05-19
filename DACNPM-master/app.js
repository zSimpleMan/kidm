const express = require('express');
const morgan = require('morgan');
const cors = require('cors');
const app = express();
const bodyParser = require('body-parser');

app.use(cors());
app.use(morgan('dev'));
app.use(express.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());


app.post('/', (req, res) => {
    res.json({
        login: req.body.abc
    })
})


app.use('/api/users', require('./routes/user.route'));

app.use('/api/auth', require('./routes/auth.route'));

const PORT = 3000;
app.listen(PORT ,() => {
    console.log(`API is running at http://localhost:${PORT}`);
})