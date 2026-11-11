import { useContext } from 'react'
import './App.css'
import NewProject from './components/NewProject'
import NoProjectSelected from './components/NoProjectSelected'
import ProjectSidebar from './components/ProjectSidebar'
import SelectedProject from './components/SelectedProject'
import { ManagementTodoStore } from './store/data-context'

function App() {
  const { selectedProjectId, onStartAddProject, todos, onSelectedProject } =
    useContext(ManagementTodoStore);

  let content = <SelectedProject />;
  if (selectedProjectId === null) {
    content = <NewProject />
  } else if (selectedProjectId === undefined) {
    content = <NoProjectSelected />
  }

  return (
    <>
      <main className="h-screen my-8 flex gap-8 ">
        <ProjectSidebar onStartAddProject={onStartAddProject} projects={todos} onSelectProject={onSelectedProject} selectedProjectId={selectedProjectId as string} />
        {content}
      </main>
    </>
  )
}

export default App
