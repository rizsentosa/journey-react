export type TeacherRequest = {
  fullname: string,
  nuptk: string,
  nip: string,
  nik: string,
  placeOfBirth: string,
  dateBirth: Date,
  phone: string,
  gender: string,
  religion: string,
  address: string,
  image: File,
  education: Array<TeacherHistoryEducation>,
};

export type TeacherHistoryEducation = {
  school: string,
  major: string,
  degree: string,
  startDate: Date,
  endDate: Date
}
