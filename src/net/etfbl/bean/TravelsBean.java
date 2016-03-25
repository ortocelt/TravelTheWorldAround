package net.etfbl.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

import net.etfbl.dao.PhotoDao;
import net.etfbl.dao.TravelsDao;
import net.etfbl.dto.Travels;

@ManagedBean
@SessionScoped
public class TravelsBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean newTravelFirstPage = true;
	private boolean newTravelAddPhotos = false;
	private Part uploadedFile;
	private Travels newTravel = new Travels();
	private ArrayList<String> uploadedPhotos = new ArrayList<String>();
	private ArrayList<Travels> userTravels = new ArrayList<Travels>();
	private Travels travelForEdit;
	private Travels openTravel;
	private ArrayList<String> allTravelPhotos = new ArrayList<String>();

	/**
	 * Show photo adding, not travel input
	 */
	public String newTravelAddPhotosChangeView() {
		Travels travel = new Travels();
		travel.setAuthor(UserBean.userIdentifier);
		travel.setTitle(newTravel.getTitle());
		travel.setBrief(newTravel.getBrief());
		travel.setKeywords(newTravel.getKeywords());
		travel.setLocation(newTravel.getLocation());
		travel.setText(newTravel.getText());

		int userId = UserBean.userIdentifier;
		newTravel.setId(TravelsDao.addNewTravelData(userId, travel.getTitle(),
				travel.getText(), travel.getBrief(), travel.getKeywords(),
				travel.getLocation()));
		setNewTravelFirstPage(false);
		setNewTravelAddPhotos(true);
		return "/user/userUploadPhotos.xhtml?faces-redirect=true";
	}

	public void openAddedPhoto(String path) {

	}

	public String userIdentification() {
		return UserBean.userNameAndSurnameByID(openTravel.getAuthor());
	}

	public String openTravelPage(int id) {
		openTravel = TravelsDao.selectTravel(id);
		allTravelPhotos = TravelsDao.allTravelPhotos(id);
		return "/user/userTravelPage.xhtml?faces-redirect=true";

	}

	/**
	 * Reset page rendering to show Travel input, not Photo adding
	 */
	public void newTravelEditTravelChangeView() {
		setNewTravelFirstPage(true);
		setNewTravelAddPhotos(false);
	}

	public void newTravelPhotoUpload() {
		try {

			System.out.println(newTravel.getTitle());

			ServletContext servletContext = (ServletContext) FacesContext
					.getCurrentInstance().getExternalContext().getContext();
			String fileLocation = "";
			// servletContext.getRealPath("Alien2.bmp");
			// servletContext.getContextPath();
			// fileLocation += File.separator + "resources" + File.separator +
			// "photos" + File.separator + newTravel.getTitle() +
			// File.separator;
			fileLocation = "C:\\Users\\Milos\\workspace"
					+ "\\TravelTheWorldAroundIP2016"
					+ "\\TravelTheWorldAround\\WebContent\\photos\\" + UserBean.userIdentifier + "\\" + newTravel.getTitle() + "\\";

			// fileLocation = "C:\\ipPhotos\\" + UserBean.userIdentifier + "\\"
			// + newTravel.getTitle() + "\\";
			// fileLocation += "/photos/";
			InputStream is = uploadedFile.getInputStream();
			// byte[] fileForUpload = NaucniRadBean.serialize(is);
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();

			int nRead;
			byte[] data = new byte[16384];

			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();

			byte[] fileForUpload = buffer.toByteArray();
			// byte[] fileForUpload = IOUtils.toByteArray(is);

			String fileName = fileLocation + getFileName(uploadedFile);
			File filePath = new File(fileLocation);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			upload(fileForUpload, fileName);
			String insertPath = "/photos/" + UserBean.userIdentifier + "/" + newTravel.getTitle() + "/" + getFileName(uploadedFile);
			PhotoDao.uploadPhoto(UserBean.userIdentifier, newTravel.getId(),
					insertPath);
			listFilesForFolder(new File(fileLocation));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void listFilesForFolder(final File folder) {
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				uploadedPhotos.add(fileEntry.getName());
			}
		}
	}

	private static String getFileName(Part part) {
		for (String cd : part.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1)
						.substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
			}
		}
		return null;
	}

	public String upload(byte[] uploadedFile, String filename) {
		try {

			// byte[] in = service.downloadFile("projektni.pdf");
			InputStream is = new ByteArrayInputStream(uploadedFile);
			/* Location for downloading and storing in client’s machine */
			DataHandler dataHandler = new DataHandler(
					new InputStreamDataSource(is));
			File file = new File(filename);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream outputStream = new FileOutputStream(file);

			dataHandler.writeTo(outputStream);
			outputStream.flush();
			outputStream.close();
			is.close();
			return "Fajl je uspjesno uploadovan";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Doslo je do greske";
	}

	public class InputStreamDataSource implements DataSource {
		private InputStream inputStream;

		public InputStreamDataSource(InputStream inputStream) {
			this.inputStream = inputStream;
		}

		@Override
		public InputStream getInputStream() throws IOException {
			return inputStream;
		}

		@Override
		public OutputStream getOutputStream() throws IOException {
			throw new UnsupportedOperationException("Not implemented");
		}

		@Override
		public String getContentType() {
			return "*/*";
		}

		@Override
		public String getName() {
			return "InputStreamDataSource";
		}
	}

	/**
	 * @return the newTravelFirstPage
	 */
	public boolean isNewTravelFirstPage() {
		return newTravelFirstPage;
	}

	/**
	 * @param newTravelFirstPage
	 *            the newTravelFirstPage to set
	 */
	public void setNewTravelFirstPage(boolean newTravelFirstPage) {
		this.newTravelFirstPage = newTravelFirstPage;
	}

	/**
	 * @return the newTravelAddOPhotos
	 */
	public boolean isNewTravelAddPhotos() {
		return newTravelAddPhotos;
	}

	/**
	 * @param newTravelAddOPhotos
	 *            the newTravelAddOPhotos to set
	 */
	public void setNewTravelAddPhotos(boolean newTravelAddOPhotos) {
		this.newTravelAddPhotos = newTravelAddOPhotos;
	}

	/**
	 * @return the uploadedFile
	 */
	public Part getUploadedFile() {
		return uploadedFile;
	}

	/**
	 * @param uploadedFile
	 *            the uploadedFile to set
	 */
	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	/**
	 * @return the newTravel
	 */
	public Travels getNewTravel() {
		return newTravel;
	}

	/**
	 * @param newTravel
	 *            the newTravel to set
	 */
	public void setNewTravel(Travels newTravel) {
		this.newTravel = newTravel;
	}

	/**
	 * @return the uploadedPhotos
	 */
	public ArrayList<String> getUploadedPhotos() {
		return uploadedPhotos;
	}

	/**
	 * @param uploadedPhotos
	 *            the uploadedPhotos to set
	 */
	public void setUploadedPhotos(ArrayList<String> uploadedPhotos) {
		this.uploadedPhotos = uploadedPhotos;
	}

	/**
	 * @return the userTravels
	 */
	public ArrayList<Travels> getUserTravels() {
		return userTravels;
	}

	/**
	 * @param userTravels
	 *            the userTravels to set
	 */
	public void setUserTravels(ArrayList<Travels> userTravels) {
		this.userTravels = userTravels;
	}

	public String userEditTravels() {
		userTravels = TravelsDao.userTravels(UserBean.userIdentifier);
		return "/user/userEditTravels.xhtml?faces-redirect=true";
	}

	public String editTravel(int travelId) {
		setTravelForEdit(TravelsDao.selectTravel(travelId));
		return "/user/userTravelEdit.xhtml?faces-redirect=true";
	}

	/**
	 * @return the travelForEdit
	 */
	public Travels getTravelForEdit() {
		return travelForEdit;
	}

	/**
	 * @param travelForEdit
	 *            the travelForEdit to set
	 */
	public void setTravelForEdit(Travels travelForEdit) {
		this.travelForEdit = travelForEdit;
	}

	/**
	 * @return the openTravel
	 */
	public Travels getOpenTravel() {
		return openTravel;
	}

	/**
	 * @param openTravel
	 *            the openTravel to set
	 */
	public void setOpenTravel(Travels openTravel) {
		this.openTravel = openTravel;
	}

	/**
	 * @return the allTravelPhotos
	 */
	public ArrayList<String> getAllTravelPhotos() {
		return allTravelPhotos;
	}

	/**
	 * @param allTravelPhotos
	 *            the allTravelPhotos to set
	 */
	public void setAllTravelPhotos(ArrayList<String> allTravelPhotos) {
		this.allTravelPhotos = allTravelPhotos;
	}

}
