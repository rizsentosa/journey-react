import type { SelectHTMLAttributes } from "react";

export type SelectOptionItem = {
  label: string;
  value: string | number;
}

type Props = {
  label?: string;
  error?: string;
  helperText?: string;
  options: SelectOptionItem[];
} & SelectHTMLAttributes<HTMLSelectElement>;

export default function SelectOption({ label,
  error,
  helperText,
  options,
  className = '',
  disabled,
  id,
  ...props }: Props) {
  const selectId = id || (label ? label.toLowerCase().replace(/\s+/g, '-') : undefined);

  return (
    <div className="w-full flex flex-col gap-1.5">
      {label && (
        <label
          htmlFor={selectId}
          className="text-sm font-medium text-slate-100 tracking-wide"
        >
          {label}
        </label>
      )}

      <div className="relative">
        <select
          id={selectId}
          disabled={disabled}
          className={`w-full appearance-none px-4 py-2.5 pr-10 text-slate-900 bg-white border rounded-xl outline-none transition-all duration-200 shadow-sm cursor-pointer ${error
            ? 'border-red-500 focus:ring-2 focus:ring-red-500/20 focus:border-red-500'
            : 'border-purple-900 hover:border-slate-400 focus:border-purple-500 focus:ring-2 focus:ring-purple-500/20'
            } ${disabled ? 'opacity-50 cursor-not-allowed bg-slate-100' : ''
            } ${className}`}
          {...props}
        >
          {options.map((option) => (
            <option key={option.value} value={option.value} className="text-slate-900 rounded-xl">
              {option.label}
            </option>
          ))}
        </select>

        <div className="absolute inset-y-0 right-0 flex items-center px-4 pointer-events-none text-slate-400">
          <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M19 9l-7 7-7-7" />
          </svg>
        </div>
      </div>

      {(error || helperText) && (
        <p className={`text-xs ${error ? 'text-red-500 font-medium' : 'text-slate-900'}`}>
          {error || helperText}
        </p>
      )}
    </div>
  );
}