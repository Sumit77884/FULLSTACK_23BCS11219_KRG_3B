import React, { useState } from 'react'

export default function StudentForm({ onSubmit, initial = {} }){
  const [form, setForm] = useState({
    firstName: initial.firstName || '',
    lastName: initial.lastName || '',
    email: initial.email || '',
    age: initial.age || '',
    course: initial.course || ''
  });

  function change(e){
    setForm({...form, [e.target.name]: e.target.value});
  }

  function submit(e){
    e.preventDefault();
    const payload = { ...form };
    if(payload.age === '') delete payload.age;
    onSubmit(payload);
  }

  return (
    <form onSubmit={submit} className="form">
      <label>First name: <input name="firstName" value={form.firstName} onChange={change} required /></label>
      <label>Last name: <input name="lastName" value={form.lastName} onChange={change} required /></label>
      <label>Email: <input name="email" value={form.email} onChange={change} type="email" required /></label>
      <label>Age: <input name="age" value={form.age} onChange={change} type="number" min="0" /></label>
      <label>Course: <input name="course" value={form.course} onChange={change} /></label>
      <button type="submit">Save</button>
    </form>
  )
}
