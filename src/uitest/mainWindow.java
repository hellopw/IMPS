package uitest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import socket.Client;
import socket.Constants;
import socket.ServerHandler;
import socket.Client.Cs;
import socket.Client.Recv;
import Encryption.AEScrypt;
import Encryption.Hash;
import bean.Friends;
import bean.Message;
import bean.Category;
import bean.Msg;
import bean.User;
import bean.UserInfo;

public class mainWindow extends JFrame {
	
	public int add_sign;                    //�Ӻ��ѱ�־
	
	public String IP = Constants.SERVER_IP; // ������IP
	public int PORT = Constants.PORT;

	private static final int DEFAULT_WIDTH = 350;
	private static final int DEFAULT_HEIGHT = 450;
	public static User user;

	public mainWindow(final User user, ArrayList<Friends> fl) {
		//this.user=user;
		//this.fl=fl;
		init(user,fl);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				 System.out.println("������");
				Msg msg = new Msg("outline", user);
				String send = ServerHandler.bianma(msg);
				// Client c=new Client();
				Cs cs = new Cs(IP, PORT, send);			
				cs.run();			
				System.exit(0);
			}
		});
		
	}

	public void init(final User user, ArrayList<Friends> fl) {
		final DefaultTreeModel model = getTree(fl);
		final JTree tree = new JTree(model);

		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setLayout(null);
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		final JTextField tf = new JTextField();
		final JTextArea showmessage = new JTextArea();

		final JLabel label1 = new JLabel(
				new ImageIcon(
						"E:\\BaiduNetdiskDownload\\FEIQQ\\FEIQQ\\src\\feiqq\\resource\\image\\avatar.png"));     //ͷ��
		JLabel label2 = new JLabel(user.getNickName());
		JLabel label3 = new JLabel("" + user.getAccount());
		JLabel label4 = new JLabel(user.getSignature());
		panel1.setPreferredSize(new Dimension(400, 50));
		panel1.setLayout(new GridLayout(1, 4));
		panel1.add(label1);
		panel1.add(label2);
		panel1.add(label3);
		panel1.add(label4);
		panel1.setBounds(20, 5, 300, 60);
		// panel1.setBackground(Color.cyan);
		add(panel1);

		MouseListener ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getSource() == tree) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					if (node.isLeaf()) {
						if (e.getClickCount() == 2) {

							String name = node.toString();

							User user1 = new User("", 0, "", ""); // Ҫ���͵��û���Ϣ
							user1.setNickName(name);
							System.out.println(user1.getNickName());

							Msg msg = new Msg("get", user1);

							String send = ServerHandler.bianma(msg);
							// Client c=new Client();
							Cs cs = new Cs(IP, PORT, send);
							String mess = cs.run();
							System.out.println(mess);
							int n = Check.check(mess);
							if (n > 0) {
								User user2 = Check.getUser(); // �õ����û���Ϣ
								UserInfo userinfo = Check.getUI();

								ChatRoom cr = new ChatRoom(user, user2, userinfo); // User
								cr.setVisible(true);
							} else {
								JOptionPane.showMessageDialog(null, "�û�������",
										"alter", JOptionPane.ERROR_MESSAGE);
							}
						}
					} else {
						// showmessage.append("����Ҷ��\n");
					}
				}
			}
		};
		label1.addMouseListener(ml);
		tree.addMouseListener(ml);
		tree.setRowHeight(30); // �ڵ����
		DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer) tree
				.getCellRenderer();
		cellRenderer
				.setLeafIcon(new ImageIcon(
						"E:\\BaiduNetdiskDownload\\FEIQQ\\FEIQQ\\src\\feiqq\\resource\\image\\qq_icon1.png"));
		cellRenderer
				.setOpenIcon(new ImageIcon(
						"E:\\BaiduNetdiskDownload\\FEIQQ\\FEIQQ\\src\\feiqq\\resource\\image\\qq_icon1.png"));
		cellRenderer
				.setClosedIcon(new ImageIcon(
						"E:\\BaiduNetdiskDownload\\FEIQQ\\FEIQQ\\src\\feiqq\\resource\\image\\qq_icon1.png"));
		cellRenderer.setFont(new Font("����", Font.PLAIN, 18)); // ��������.
		cellRenderer.setBackgroundNonSelectionColor(Color.white);
		cellRenderer.setBackgroundSelectionColor(Color.yellow);
		cellRenderer.setBorderSelectionColor(Color.red);
		cellRenderer.setTextNonSelectionColor(Color.blue);
		cellRenderer.setTextSelectionColor(Color.red);
		JScrollPane scrollPane1 = new JScrollPane(tree);
		scrollPane1.setBounds(5, 10, 220, 260);

		scrollPane1.setOpaque(false); // ����͸��
		scrollPane1.getViewport().setOpaque(false);
		// scrollPane1.setForeground(Color.blue);
		// panel2.setBackground(Color.yellow);

		panel2.add(scrollPane1);

		/*
		 * { tf.setBounds(170,50,120,30); panel2.add(tf);
		 * 
		 * JButton button4 = new JButton("search");
		 * button4.addActionListener(new ActionListener(){ public void
		 * actionPerformed(ActionEvent event){ String s=tf.getText(); //���в��� }
		 * }); button4.setBounds(170,10,80,30); panel2.add(button4);
		 * 
		 * 
		 * showmessage.setEditable(false);
		 * showmessage.setForeground(Color.blue); //showmessage.setFont(new
		 * Font("����",Font.BOLD,15)); //showmessage.append("s"+" �������Ľ����: \n");
		 * // showmessage.append("ͷ��   \n "+" С��\n"+"   1290329029\n");
		 * JScrollPane scrollPane2 = new JScrollPane(showmessage);
		 * //scrollPane2.getViewport().setBackground(Color.black);
		 * scrollPane2.setBounds(170,100,120,150); panel2.add(scrollPane2);
		 * 
		 * panel2.setBounds(20,60,300,280); //panel2.setBackground(Color.blue);
		 * panel2.setLayout(null); add(panel2); }
		 */
		panel2.setBounds(20, 60, 300, 280);
		// panel2.setBackground(Color.blue);
		panel2.setLayout(null);
		add(panel2);
		{
			JButton button2 = new JButton("add friend");
			button2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					String input_name = JOptionPane
							.showInputDialog("Please input name"); // �����Ի���

					User user1 = new User(0, input_name); // Ҫ���͵��û���Ϣ
					User[] users = new User[2];
					users[0] = user;
					users[1] = user1;
					Msg msg = new Msg("add_req_server", users, "�ҵĺ���");
					String send = ServerHandler.bianma(msg);
					// Client c=new Client();
					Cs cs = new Cs(IP, PORT, send);
					String mess=cs.run();
					
					int n = Check.check(mess);
					if (n > 0) {
						String s = input_name;
						DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
								.getLastSelectedPathComponent();
						if (selectedNode == null)
							return;
						DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
								s);
						model.insertNodeInto(newNode, selectedNode, 0); // selectedNode.getChildCount()+1

						TreeNode[] nodes = model.getPathToRoot(newNode);
						TreePath path = new TreePath(nodes);
						tree.scrollPathToVisible(path);
						JOptionPane.showMessageDialog(null, "��ӳɹ�", "alter",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "���ʧ��", "alter",
								JOptionPane.ERROR_MESSAGE);
					}
					/*
					 * String mess=cs.run(); int n=Check.check(mess); if(n>0){
					 * JOptionPane.showMessageDialog(null, "alter", "��ӳɹ�",
					 * JOptionPane.ERROR_MESSAGE); }else{
					 * JOptionPane.showMessageDialog(null, "alter", "���ʧ��",
					 * JOptionPane.ERROR_MESSAGE); }
					 */
					/*String s = input_name;
					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					if (selectedNode == null)
						return;
					DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
							s);
					model.insertNodeInto(newNode, selectedNode, 0); // selectedNode.getChildCount()+1

					TreeNode[] nodes = model.getPathToRoot(newNode);
					TreePath path = new TreePath(nodes);
					tree.scrollPathToVisible(path);*/
				}
			});
			JButton button3 = new JButton("delete");
			button3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {

					DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
							.getLastSelectedPathComponent();
					if (selectedNode != null) // &&
												// selectedNode.getParent()!=null
						model.removeNodeFromParent(selectedNode);

					String name = selectedNode.toString();
					User user1 = new User(0, name); // Ҫ���͵��û���Ϣ

					User[] users = new User[2];
					users[0] = user;
					users[1] = user1;
					Msg msg = new Msg("delete", users, "");

					String send = ServerHandler.bianma(msg);
					// Client c=new Client();
					Cs cs = new Cs(IP, PORT, send);
					String mess = cs.run();

					int n = Check.check(mess);
					if (n > 0) {
						JOptionPane.showMessageDialog(null, "ɾ���ɹ�", "alter",
								JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "ɾ��ʧ��", "alter",
								JOptionPane.ERROR_MESSAGE);
					}

				}
			});

			panel3.setLayout(new GridLayout(1, 2));
			panel3.add(button2);
			panel3.add(button3);
			panel3.setBounds(20, 350, 300, 40);
			add(panel3);

		}

		{
			ImageIcon img = new ImageIcon(
					"E:/BaiduNetdiskDownload/FEIQQ/FEIQQ/src/feiqq/resource/image/back11.png");
			JLabel imgLabel = new JLabel(img);
			this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE));
			imgLabel.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
			Container contain = this.getContentPane();
			((JPanel) contain).setOpaque(false);

		}

		/*Recv2 r = new Recv2(user);
		Thread t = new Thread(r);
		t.start();*/
		
		add_friend a2=new add_friend(tree,model,"�ҵĺ���");
		Thread t2=new Thread(a2);
		t2.start();
		
	}
   
	
	public DefaultTreeModel getTree(ArrayList<Friends> fl) { // ����Ϊ�����б�
		DefaultTreeModel model;
		JTree tree;
		
		String cata1="����";
		String cata2="����";
		String fri;
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("�����б�");
		DefaultMutableTreeNode catagory1 = new DefaultMutableTreeNode(cata1);
		root.add(catagory1);	
		DefaultMutableTreeNode catagory2 = new DefaultMutableTreeNode(cata2);
		root.add(catagory2);	
		int i = 0;	
		while (i < fl.size()) {			
			/*cata = fl.get(i).getGroup();
			fri = fl.get(i).getFriend();
			DefaultMutableTreeNode catagory = new DefaultMutableTreeNode(cata);
			root.add(catagory);
			DefaultMutableTreeNode friend = new DefaultMutableTreeNode(fri);
			catagory.add(friend);			
			i++;*/			
			
			if(fl.get(i).getState().equals("1")){				
				fri = fl.get(i).getFriend();					
				DefaultMutableTreeNode friend = new DefaultMutableTreeNode(fri);
				catagory1.add(friend);
			}else if(fl.get(i).getState().equals("0")){				
				fri = fl.get(i).getFriend();				
				DefaultMutableTreeNode friend = new DefaultMutableTreeNode(fri);
				catagory2.add(friend);
			}
			i++;
		}
		model = new DefaultTreeModel(root);
		return model;

	}

	public static void main(String[] args) {
		User user = new User("С��", 12345, "123456", "qq����");
		Friends fri = new Friends("wnag", "ͬѧ", "zhang", "����");
		ArrayList<Friends> fl = new ArrayList<Friends>();
		fl.add(fri);
		JFrame f = new mainWindow(user, fl);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}

}

class Recv2 implements Runnable {
	public User user;
	public JTextArea ta;
	public static String mess;
	ServerSocket serverSocket;

	public Recv2(User u) {
		user = u;
	}

	public void run() {
		InetAddress ip;
		try {
			ip = InetAddress.getLocalHost();
			try {
				serverSocket = new ServerSocket(Constants.PORT, 100, ip);
			} catch (IOException e) {

				e.printStackTrace();
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				try {
					DataInputStream input = new DataInputStream(
							socket.getInputStream());
					String sss = input.readUTF();

					String[] ss = sss.split(",");
					String encryptResultStr = ss[0];
					byte[] decryptFrom = AEScrypt
							.parseHexStr2Byte(encryptResultStr); // ����
					byte[] decryptResult = AEScrypt.decrypt(decryptFrom,
							Constants.KEY);

					mess = new String(decryptResult, "utf-8"); // ��ϣ��֤
					if (!ss[1].equals(Hash.hex_sha1(mess))) {
						mess = "��ϣ��֤ûͨ��";
						break;
					}

					input.close();
				} catch (Exception e) {
					System.out.println("������ run �쳣: " + e.getMessage());
				} finally {
					if (socket != null) {
						try {
							socket.close();
						} catch (Exception e) {
							socket = null;
							System.out.println("����� finally �쳣:"
									+ e.getMessage());
						}
					}
				}
			} catch (Exception e) {
				System.out.println("�������쳣: " + e.getMessage());
			}
			int n = Check.check(mess);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Message mess2 = Check.getMessage();

			if (ChatRoom.getStates(user,
					mess2.getSenderName()) == 0) {
				String name = mess2.getSenderName();

				User user1 = new User("", 0, "", ""); // Ҫ���͵��û���Ϣ
				user1.setNickName(name);
				System.out.println(user1.getNickName());

				Msg msg = new Msg("get", user1);

				String send = ServerHandler.bianma(msg);
				// Client c=new Client();
				Cs cs = new Cs(Constants.SERVER_IP, Constants.PORT, send);
				String mess = cs.run();
				System.out.println(mess);
				int n1 = Check.check(mess);
				if (n1 > 0) {
					User user2 = Check.getUser(); // �õ����û���Ϣ
					UserInfo userinfo = Check.getUI();

					ChatRoom cr = new ChatRoom(user, user2, userinfo); // User
					cr.setVisible(true);
					cr.getArea().append(
							mess2.getSenderName() + " " + mess2.getDate()
									+ "\n" + "   " + mess2.getContent() + "\n");
				}

			} else {
				new ChatRoom(user, mess2.getSenderName()).getArea().append(
						mess2.getSenderName() + " " + mess2.getDate() + "\n"
								+ "   " + mess2.getContent() + "\n");
			}
		}

	}
}

class add_friend implements Runnable{
     public int add;
	JTree tree ;
	DefaultTreeModel model;
	String name;
	public add_friend(JTree t,DefaultTreeModel m,String s){		
		tree=t;
		model=m;
		name=s;
	}
	public void run() {
		while((add=Check.add_sign)!=1){
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
		}
		if((add=Check.add_sign)==1){
			DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree
					.getLastSelectedPathComponent();
			if (selectedNode == null)
				return;
			DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(
					name);
			model.insertNodeInto(newNode, selectedNode, 0); // selectedNode.getChildCount()+1	
			Check.add_sign=0;
		}
		
	}	
}
