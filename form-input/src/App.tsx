import { useState } from "react"
import Navbar from "./components/navbar";
import Teacher from "./pages/teacher";



type choose = "teacher" | "personnel" | "education" | "subjects" | "assigments";

export default function App() {
  const [selection, setSelection] = useState<choose>("teacher");

  const handleSelection = (select: string) => {
    setSelection(select as choose);
  }

  return <>
    <main className="bg-slate-900 min-h-screen">
      <div className="container mx-auto pt-12">
        <Navbar currentSelect={selection} onSelect={handleSelection} />
        {selection === "teacher" && (<Teacher />)}
        {selection === "personnel" && (<p>personnel render</p>)}
        {selection === "education" && (<p>education render</p>)}
        {selection === "subjects" && (<p>subjects render</p>)}
        {selection === "assigments" && (<p>assigments render</p>)}
      </div>
    </main>
  </>
}