import { useActionState, useState } from "react";
import TeacherPersonalInfo from "./teacher-personalinfo";
import EducationSection from "./education-section";
import type { EducationItem } from "./education-section";
import { teacherInputSchema } from "../util/validation";

const initialErrorSubmit: Record<string, string> = {
  fullname: '',
  nuptk: '',
  nip: '',
  nik: '',
  placeOfBirth: '',
  dateBirth: '',
  phone: '',
  gender: '',
  religion: '',
  email: '',
  image: '',
};

type FormActionState = {
  values: Record<string, any>;
};

export default function FormTeacher() {
  const [educations, setEducations] = useState<EducationItem[]>([
    {
      id: crypto.randomUUID(),
      schoolName: '',
      degree: '',
      major: '',
      startDateEducation: '',
      endDateEducation: ''
    },
  ]);

  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [previewUrl, setPreviewUrl] = useState<string | null>(null);
  const [error, setError] = useState<Record<string, string>>(initialErrorSubmit);

  const handleAddEducation = () => {
    setEducations((prev) => [
      ...prev,
      {
        id: crypto.randomUUID(),
        schoolName: '',
        degree: '',
        major: '',
        startDateEducation: '',
        endDateEducation: ''
      },
    ]);
  };

  const handleRemoveEducation = (idToRemove: string) => {
    setEducations((prev) => prev.filter((item) => item.id !== idToRemove));
  };

  const handleEducationChange = (index: number, field: keyof EducationItem, value: string) => {
    setEducations((prev) => {
      const newEducations = [...prev];
      newEducations[index] = { ...newEducations[index], [field]: value };
      return newEducations;
    });

    const errorKey = `educations.${index}.${field}`;
    if (error[errorKey]) {
      setError((prev) => ({ ...prev, [errorKey]: '' }));
    }
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name } = e.target;
    if (name === 'image') {
      const file = e.target.files?.[0];
      if (file) {
        const fileUrl = URL.createObjectURL(file);
        setSelectedFile(file);
        setPreviewUrl(fileUrl);
      }
    }

    if (error[name]) {
      setError((prev) => ({ ...prev, [name]: '' }));
    }
  };

  const submitAction = async (_: FormActionState, fd: FormData): Promise<FormActionState> => {
    const currentFileInForm = fd.get("image");
    if (selectedFile !== null) {
      const isFormFileEmpty = !(currentFileInForm instanceof File) || currentFileInForm.size === 0;
      if (isFormFileEmpty) {
        fd.set('image', selectedFile);
      }
    }

    const formValue = {
      ...Object.fromEntries(fd.entries()),
      educations,
    };

    const result = teacherInputSchema.safeParse(formValue);

    if (!result.success) {
      const formattedErrors: Record<string, string> = {};
      result.error.issues.forEach((issue) => {
        const fieldPath = issue.path.join('.');
        if (!formattedErrors[fieldPath]) {
          formattedErrors[fieldPath] = issue.message;
        }
      });

      setError({
        ...initialErrorSubmit,
        ...formattedErrors,
      });

      return { values: formValue };
    }

    for (const [key, value] of fd.entries()) {
      console.log(`${key}: ${value}`)
    }

    try {
      await fetch('http://localhost:8080/api/v1/public/teacher', {
        method: "POST",
        headers: {
          "My-Token": "qwerty123@"
        },
        body: fd,
      });
    } catch (err) {
      console.error("Gagal mengirim data:", err);
    }

    setSelectedFile(null);
    setPreviewUrl(null);
    setError(initialErrorSubmit);
    setEducations([{ id: crypto.randomUUID(), schoolName: '', degree: '', major: '', startDateEducation: '', endDateEducation: '' }]);

    return { values: {} };
  };

  const [state, formAction, isPending] = useActionState(submitAction, {
    values: {},
  });

  return (
    <form action={formAction} className="w-full border border-slate-100 p-6 rounded-2xl shadow-xl flex flex-col gap-6">
      <TeacherPersonalInfo
        values={state.values || {}}
        errors={error}
        previewUrl={previewUrl}
        selectedFileName={selectedFile?.name}
        onChange={handleInputChange}
      />

      <EducationSection
        educations={educations}
        errors={error}
        onAdd={handleAddEducation}
        onRemove={handleRemoveEducation}
        onChange={handleEducationChange}
      />

      <div className="flex items-center justify-end gap-4 pt-4 border-t border-slate-200">
        <button
          type="reset"
          className="px-12 py-2.5 text-sm font-semibold text-slate-700 bg-white border border-slate-300 rounded-xl hover:bg-slate-50 cursor-pointer"
        >
          Reset
        </button>

        <button
          type="submit"
          disabled={isPending}
          className="px-14 py-2.5 text-sm font-semibold text-white bg-cyan-700 rounded-xl hover:bg-cyan-500 cursor-pointer disabled:opacity-50"
        >
          {isPending ? 'Saving...' : 'Save'}
        </button>
      </div>
    </form>
  );
}