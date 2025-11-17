import React from 'react'
import { Header } from './Header'

export default function App() {
  const username = 'Sumit Singh'
  return (
    <div>
      <Header username={username} />
    </div>
  )
}
