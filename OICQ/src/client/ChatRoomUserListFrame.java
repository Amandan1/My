package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import readThread.ReadMessageFromServerThread;
import stream.ClientToClientConnectionStream;
import stream.ServerClientConnectionStream;
import tools.MyMap;
import user.UserInformation;

public class ChatRoomUserListFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MyMap isOnlineMap;
	private MyMap isOpenMap;
	private ServerClientConnectionStream userCS;
	private JList<String> currentOnlineUserList;
	private Map<String, String> userInfoMap;
	private DatagramSocket dataSocket;
	private UserInformation userInfo;
	private ReadMessageFromServerThread readMessageFromServerThread;
	private Thread readMessThread;
	private ChatRoomClientFrame chatRoomClientFrame;
	private Map<String, ChatRoomClientFrame> chatRoomMap = new HashMap<String, ChatRoomClientFrame>();

	public ChatRoomUserListFrame(ServerClientConnectionStream userCS,
			UserInformation userInfo) throws HeadlessException {
		super("MYQQ精简版V1.0");
		this.userCS = userCS;
		this.userInfo = userInfo;
		initData();
		createFrame();
		addEventHandler();
		autoListening();
	}

	private void addEventHandler() {
		currentOnlineUserList
				.addListSelectionListener(new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						if (!e.getValueIsAdjusting()) {
							String tString = currentOnlineUserList
									.getSelectedValue();
							if (tString != null) {
								String t[] = tString.split("(");
								String string[] = tString.split(")");
								// 姓名-账号-IP-端口
								if (string[0].equals(userInfo.getAccount())) {
									JOptionPane
											.showMessageDialog(null, "不能选自己");
								} else {
									String tempUserInfo[] = userInfoMap.get(
											string[0]).split("-");
									UserInformation toUserInfo = new UserInformation(
											tempUserInfo[3], tempUserInfo[2],
											tempUserInfo[0], Integer
													.parseInt(tempUserInfo[1]),
											Integer.parseInt(tempUserInfo[4]),
											tempUserInfo[0], Integer
													.parseInt(tempUserInfo[1]));
									chatRoomClientFrame = new ChatRoomClientFrame(
											toUserInfo, userInfo, isOpenMap);
									chatRoomClientFrame.showMe();
									chatRoomMap.put(tempUserInfo[1],
											chatRoomClientFrame);
									isOpenMap.replace(tempUserInfo[1], true);
								}
							}
						}
					}
				});
		addWindowListener(new WindowAdapter() {
			@SuppressWarnings("unused")
			public void winddowClosing(WindowEvent e) {
				int t = JOptionPane.showConfirmDialog(null,
						"确认退出客户端么\n你温馨提示退出同时关闭所有聊天窗口", "确认退出",
						JOptionPane.OK_CANCEL_OPTION);
				if (t == JOptionPane.OK_OPTION) {
					readMessThread.interrupt();
					readMessageFromServerThread.stopMe();
					userCS.send("%EXIT%:" + userInfo.getAccount());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					userCS.send("%EXIT%:" + userInfo.getAccount());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					System.exit(0);
				}

			}
		});
	}

	public void showMe() {
		setSize(140,700);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		readMessageFromServerThread=new ReadMessageFromServerThread(userCS,userInfoMap,currentOnlineUserList,isOnlineMap,isOpenMap);
		readMessThread=new Thread(readMessageFromServerThread);
		readMessThread.start();
	}

	private void autoListening() {
		final ClientToClientConnectionStream userDataSocketCS = new ClientToClientConnectionStream(
				dataSocket);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					String string = userDataSocketCS.read();
					System.out
							.println("自动监听读取到的:"
									+ string
									+ "端口号"
									+ userDataSocketCS.getUserReceivePacket()
											.getPort());
					if (!"%TEST%".equals(string)
							&& !"I_HAVE_EXIT_THE_WINDOW".equals(string)) {
						String tString[] = string.split("-");
						if (isOpenMap.getValue(tString[1]) == true) {
							chatRoomMap
									.get(tString[1])
									.getUserDataCS()
									.setHostAddress(
											userDataSocketCS
													.getUserReceivePacket()
													.getAddress());
							chatRoomMap
									.get(tString[1])
									.getUserDataCS()
									.setPort(
											userDataSocketCS
													.getUserReceivePacket()
													.getPort());
							chatRoomMap.get(tString[1]).getUserDataCS()
									.send("%TEST%");
							chatRoomMap.get(tString[1]).getChatTextArea()
									.append(string + "\n");
						} else {
							int t = JOptionPane.showConfirmDialog(null,
									tString[0] + "给您发来一条消息,是否查收", "确认",
									JOptionPane.YES_NO_OPTION);
							if (t == JOptionPane.YES_OPTION) {
							}
							UserInformation toUserInfo = new UserInformation(
									tString[0], tString[1], userDataSocketCS
											.getUserReceivePacket()
											.getAddress().getHostAddress(),
									userDataSocketCS.getUserReceivePacket()
											.getPort(), Integer
											.parseInt(tString[2]), tString[3],
									Integer.parseInt(tString[4]));
							chatRoomClientFrame = new ChatRoomClientFrame(
									toUserInfo, userInfo, isOpenMap).showMe();
							StringBuffer tMessageBuffer = new StringBuffer();
							tMessageBuffer.append(tString[0]);
							tMessageBuffer.append(tString[5]);
							chatRoomClientFrame.getChatTextArea().append(
									tMessageBuffer + "\n");
							isOpenMap.replace(tString[1], true);
							chatRoomMap.put(tString[1], chatRoomClientFrame);
						}
					}
				}
			}
		});
	}

	private void createFrame() {
		currentOnlineUserList = new JList<String>();
		currentOnlineUserList
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		ImageIcon portraitImageIcon = new ImageIcon(getClass().getResource(
				"\\pictures\\portrait-" + userInfo.getUserPortraitNum()
						+ ".jpg"));
		portraitImageIcon.setImage(portraitImageIcon.getImage()
				.getScaledInstance(80, 80, Image.SCALE_DEFAULT));
		JLabel portraitImageLable = new JLabel(portraitImageIcon);
		JScrollPane listScrollPane = new JScrollPane(currentOnlineUserList);
		JPanel centerPanel = new JPanel(new BorderLayout());
		JPanel northJPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		northJPanel.add(portraitImageLable, BorderLayout.NORTH);
		JPanel userInfoJPanel = new JPanel(new GridLayout(2, 2, 5, 10));
		Calendar calendar = Calendar.getInstance();
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		JLabel userInfoLableJLabel = new JLabel(hour < 6 ? "凌晨好"
				+ userInfo.getName() : (hour < 12 ? "上午好," : (hour < 18 ? "下午好"
				: "晚上好")) + userInfo.getName());
		userInfoJPanel.add(userInfoLableJLabel);
		SimpleDateFormat simpleDateFormat = (SimpleDateFormat) DateFormat
				.getInstance();
		simpleDateFormat.applyPattern("yyyy年MM月dd日  E");
		@SuppressWarnings("unused")
		JLabel userTimeLabel = new JLabel(simpleDateFormat.format(new Date()));
		userInfoJPanel.add(userInfoJPanel);
		northJPanel.add(userInfoJPanel);
		centerPanel.add(new JLabel("在线用户列表:"), BorderLayout.CENTER);
		centerPanel.add(listScrollPane, BorderLayout.CENTER);
		add(northJPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSizeDimension = toolkit.getScreenSize();
		int width = getWidth();
		int screenWidth = screenSizeDimension.width / 2;
		setLocation(screenWidth - width / 2, 0);
		setResizable(false);

	}

	private void initData() {
		isOnlineMap = new MyMap();
		isOpenMap = new MyMap();
		userInfoMap = new HashMap<String, String>();
		try {
			dataSocket = new DatagramSocket(userInfo.getPort());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
