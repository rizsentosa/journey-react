import type { InputHTMLAttributes } from "react";

type Props = {
  label?: string;
  error?: string;
  helperText?: string;
} & InputHTMLAttributes<HTMLInputElement>;

const Input: React.FC<Props> = ({
  label,
  error,
  helperText,
  className = '',
  disabled,
  id,
  type = 'text',
  ...props
}) => {
  const inputId = id || (label ? label.toLowerCase().replace(/\s+/g, '-') : undefined);
  const isFile = type === 'file';
  return (
    <div className="w-full flex flex-col gap-1.5">
      {label && (
        <label
          htmlFor={inputId}
          className="text-sm font-medium text-slate-100 tracking-wide"
        >
          {label}
        </label>
      )}

      {/* Input Element */}
      <input
        id={inputId}
        type={type}
        disabled={disabled}
        className={
          isFile
            ? `w-max text-sm text-slate-500 file:mr-4 file:py-2.5 file:px-4 file:rounded-xl file:border-0 file:text-sm file:font-semibold file:bg-purple-50 file:text-slate-700 hover:file:bg-purple-100 cursor-pointer border border-purple-300 rounded-xl bg-white shadow-sm outline-none transition-all duration-200 ${className}`
            : `w-full px-4 py-2.5 text-slate-900 bg-slate-100 border rounded-xl outline-none transition-all duration-200 placeholder:text-slate-400 shadow-sm ${error
              ? 'border-red-500 focus:ring-2 focus:ring-red-500/20 focus:border-red-500'
              : 'border-purple-700 hover:border-purple-400 focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20'
            } ${disabled ? 'opacity-50 cursor-not-allowed bg-slate-100' : ''
            } ${className}`
        }
        {...props}
      />

      {/* Helper Text atau Pesan Error */}
      {(error || helperText) && (
        <p className={`text-xs ${error ? 'text-red-400 font-medium' : 'text-slate-100'}`}>
          {error || helperText}
        </p>
      )}
    </div>
  )
}

export default Input;