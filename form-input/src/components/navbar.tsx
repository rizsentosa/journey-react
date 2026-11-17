const navbar = [
  { id: 1, name: "teacher" },
  { id: 2, name: "personnel" },
  { id: 3, name: "education" },
  { id: 4, name: "subjects" },
  { id: 5, name: "assigments" }
]

type Props = {
  currentSelect: string,
  onSelect: (value: string) => void
}

function NavbarItem(
  { title, highlight, onSelection }:
    { title: string, highlight: boolean, onSelection: (value: string) => void }) {
  return (
    <div>
      <li className="group relative">
        <button onClick={() => onSelection(title)} className={`relative px-4 py-2.5 text-sm md:text-base transition-all duration-300 ease-out rounded-lg cursor-pointer ${highlight ? 'text-cyan-400 bg-slate-700 shadow-inner scale-105' : 'text-slate-300 hover:text-cyan-400 hover:scale-105'}`}>
          {title}
        </button>
      </li>
    </div>
  )
}

export default function Navbar({ currentSelect: selection, onSelect }: Props) {
  return (
    <nav className="p-4 bg-slate-900/90 backdrop-blur-md shadow-2xl border border-slate-800 rounded-xl px-6 py-2 mb-12">
      <ul className="flex justify-start items-center text-white tracking-wider uppercase font-semibold">
        {navbar.map(item => {
          const isSelected = selection === item.name;
          return <NavbarItem key={item.id} title={item.name} highlight={isSelected} onSelection={onSelect} />
        })}
      </ul>
    </nav>
  )
}