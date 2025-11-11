const express = require('express');
const router = express.Router();
const { body } = require('express-validator');
const controller = require('../controllers/studentController');

const validate = [
  body('firstName').notEmpty().withMessage('First name is required'),
  body('lastName').notEmpty().withMessage('Last name is required'),
  body('email').isEmail().withMessage('Valid email is required'),
  body('age').optional({ checkFalsy: true }).isInt({ min: 0 }).withMessage('Age must be a positive integer')
];

router.get('/', controller.getAll);
router.post('/', validate, controller.create);
router.get('/:id', controller.getOne);
router.put('/:id', validate, controller.update);
router.delete('/:id', controller.remove);

module.exports = router;
