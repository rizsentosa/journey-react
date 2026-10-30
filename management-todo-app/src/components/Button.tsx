
type TButtonProps = {
  children: React.ReactNode
} & React.ButtonHTMLAttributes<HTMLButtonElement>
export default function Button({ children, ...props }: TButtonProps) {
  return <button className="px-4 py-2 text-xs md:text-base rounded-md bg-stone-700 text-stone-400 hover:bg-stone-600 hover:text-stone-500" {...props} >
    {children}
  </button>
}