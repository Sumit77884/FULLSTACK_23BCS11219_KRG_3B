const express = require('express');
const router = express.Router();
const auth = require('../middleware/auth');
// View profile
router.get('/', auth, async (req, res) => {
  res.json(req.user);
});
// Edit profile
router.put('/', auth, async (req, res) => {
  const { name, bio, avatarUrl } = req.body;
  const user = req.user;
  if(name) user.name = name;
  if(bio !== undefined) user.bio = bio;
  if(avatarUrl !== undefined) user.avatarUrl = avatarUrl;
  await user.save();
  res.json(user);
});
module.exports = router;
