import React from 'react'
import StudentForm from '../components/StudentForm'
import API from '../api'
import { useNavigate } from 'react-router-dom'

export default function AddStudent(){
  const navigate = useNavigate();

  async function handleSubmit(data){
    await API.post('/students', data);
    navigate('/');
  }

  return (
    <div>
      <h2>Add Student</h2>
      <StudentForm onSubmit={handleSubmit} />
    </div>
  )
}
