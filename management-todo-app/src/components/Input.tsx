import { forwardRef } from "react";

type TInputProps = {
  textarea: boolean,
  label: string
} & React.InputHTMLAttributes<HTMLInputElement>
  & React.TextareaHTMLAttributes<HTMLTextAreaElement>;

const Input = forwardRef<HTMLInputElement | HTMLTextAreaElement, TInputProps>(
  ({ textarea, label, ...props }, ref) => {
    const classes = "w-full p-1 border-b-2 rounded-sm border-stone-300 bg-stone-200 text-stone-600 focus:outline-none focus:border-stone-600";
    return (
      <p className="flex flex-col gap-1 my-4">
        <label className="text-sm font-bold uppercase text-stone-500">{label}</label>
        {textarea ?
          <textarea ref={ref as React.Ref<HTMLTextAreaElement>} className={classes} {...props} /> :
          <input ref={ref as React.Ref<HTMLInputElement>} className={classes} {...props} />}
      </p>
    )
  })
export default Input;