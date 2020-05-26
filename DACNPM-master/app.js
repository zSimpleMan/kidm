const express = require('express');
const morgan = require('morgan');
const cors = require('cors');
const hbs_sections = require('express-handlebars-sections');
const bodyParser = require('body-parser');
const exphbs = require('express-handlebars');

const app = express();

app.engine('handlebars', exphbs({
    defaultLayout: 'main',
    layoutsDir: 'views/layouts',
    helpers: {
        section: hbs_sections()
    }
}));

app.set('view engine', 'handlebars');

app.use(cors());
app.use(morgan('dev'));
app.use(express.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());

app.use('/', require('./routes/home.route'));

app.post('/', (req, res) => {
    res.json({
        login: req.body.abc
    })
})


app.use('/api/users', require('./routes/user.route'));

app.use('/api/auth', require('./routes/auth.route'));

app.use('/add-adnmin', require('./routes/admin.route'));

const PORT = 3000;
app.listen(PORT, () => {
    console.log(`API is running at http://localhost:${PORT}`);
})