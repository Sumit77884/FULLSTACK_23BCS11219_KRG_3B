import React, { useEffect, useState } from 'react'
import { useParams, useNavigate } from 'react-router-dom'
import StudentForm from '../components/StudentForm'
import API from '../api'

export default function EditStudent(){
  const { id } = useParams();
  const navigate = useNavigate();
  const [initial, setInitial] = useState(null);

  useEffect(()=> {
    API.get('/students/'+id).then(r=> setInitial(r.data)).catch(console.error)
  },[id]);

  async function handleSubmit(data){
    await API.put('/students/'+id, data);
    navigate('/');
  }

  if(!initial) return <p>Loading...</p>;
  return (
    <div>
      <h2>Edit Student</h2>
      <StudentForm onSubmit={handleSubmit} initial={initial} />
    </div>
  )
}
