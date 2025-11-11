const mongoose = require('mongoose');

const studentSchema = new mongoose.Schema({
  firstName: { type: String, required: true, trim: true },
  lastName: { type: String, required: true, trim: true },
  email: { type: String, required: true, trim: true, unique: true },
  age: { type: Number, min: 0 },
  course: { type: String, trim: true }
}, { timestamps: true });

module.exports = mongoose.model('Student', studentSchema);
