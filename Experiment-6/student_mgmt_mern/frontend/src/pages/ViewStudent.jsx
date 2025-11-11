import React, { useEffect, useState } from 'react'
import { useParams, Link } from 'react-router-dom'
import API from '../api'

export default function ViewStudent(){
  const { id } = useParams();
  const [student, setStudent] = useState(null);

  useEffect(()=> {
    API.get('/students/'+id).then(r=> setStudent(r.data)).catch(console.error)
  },[id]);

  if(!student) return <p>Loading...</p>;
  return (
    <div>
      <h2>{student.firstName} {student.lastName}</h2>
      <p><strong>Email:</strong> {student.email}</p>
      <p><strong>Age:</strong> {student.age||'-'}</p>
      <p><strong>Course:</strong> {student.course||'-'}</p>
      <p><Link to="/">Back</Link></p>
    </div>
  )
}
