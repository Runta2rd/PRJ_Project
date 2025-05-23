USE [PRJ_Project]
GO
/****** Object:  Table [dbo].[items]    Script Date: 06/05/2025 6:18:13 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[items](
	[item_id] [int] IDENTITY(1,1) NOT NULL,
	[student_id] [int] NOT NULL,
	[category_name] [nvarchar](50) NOT NULL,
	[itemName] [nvarchar](255) NULL,
	[title] [nvarchar](255) NOT NULL,
	[description] [nvarchar](255) NULL,
	[image_url] [nvarchar](255) NULL,
	[is_for_giveaway] [bit] NULL,
	[is_for_rent] [bit] NULL,
	[is_for_sale] [bit] NULL,
	[rent_price] [decimal](10, 2) NULL,
	[sale_price] [decimal](10, 2) NULL,
	[created_at] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[item_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[messages]    Script Date: 06/05/2025 6:18:13 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[messages](
	[message_id] [int] IDENTITY(1,1) NOT NULL,
	[sender_id] [int] NOT NULL,
	[receiver_id] [int] NOT NULL,
	[content] [nvarchar](max) NOT NULL,
	[created_at] [date] NULL,
PRIMARY KEY CLUSTERED 
(
	[message_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[students]    Script Date: 06/05/2025 6:18:13 CH ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[students](
	[student_id] [int] IDENTITY(1,1) NOT NULL,
	[email] [nvarchar](100) NOT NULL,
	[password] [nvarchar](255) NOT NULL,
	[full_name] [nvarchar](255) NULL,
	[facebook_url] [nvarchar](255) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[student_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[items] ON 

INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (1, 2, N'Laptop', N'Laptop acer nitro 5 2022', N'Laptop cũ', N'Mô tả mẫu', N'https://tuannguyenmobile.com/wp-content/uploads/2023/12/z4902049682400_4f975391113a14ca41fc580939c53ac2-e1701857335836.jpg', 0, 0, 1, NULL, CAST(1000000.00 AS Decimal(10, 2)), CAST(N'2023-08-24' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (2, 1, N'Tai nghe', N'Tai nghe samsung earbuds 3', N'Tai nghe mới', N'Mô tả mẫu', N'https://samcenter.vn/images/thumbs/0006531_galaxy-buds-2-pro-2_1900.jpeg', 0, 0, 1, NULL, CAST(1000000.00 AS Decimal(10, 2)), CAST(N'2023-06-02' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (3, 2, N'Chuột', N'Chuột logitech G102', N'Chuột gaming', N'Mô tả mẫu', N'https://down-vn.img.susercontent.com/file/vn-11134207-7ras8-m2ko2n8hczp6dc.webphttps://down-vn.img.susercontent.com/file/vn-11134207-7ras8-m2ko2n8hczp6dc.webp', 0, 0, 1, NULL, CAST(1000000.00 AS Decimal(10, 2)), CAST(N'2023-05-22' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (4, 3, N'Sách', N'Sách trans6', N'Sách lập trình', N'Mô tả mẫu', N'https://library.fpt.edu.vn/Uploads/HN/Images/Catalogue/FPT190025878.jpg', 1, 0, 0, NULL, NULL, CAST(N'2024-12-26' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (5, 1, N'Bàn phím', N'Bàn phím EK87', N'Bàn phím cơ', N'Mô tả mẫu', N'https://gamek.mediacdn.vn/thumb_w/640/133514250583805952/2024/6/24/img2677-1719219024220960233982.jpg', 0, 1, 1, CAST(100000.00 AS Decimal(10, 2)), CAST(1000000.00 AS Decimal(10, 2)), CAST(N'2024-03-27' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (6, 2, N'Laptop', N'Laptop MSI modern 15', N'Laptop văn phòng', N'Mô tả mẫu', N'https://storage.googleapis.com/teko-gae.appspot.com/media/image/2024/4/24/fed80dfd-c712-4256-af09-e7c8d8a58683/Laptop%20MSI%20Modern%2015%20B12M%20%281%29.png', 0, 0, 0, NULL, NULL, CAST(N'2024-12-12' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (11, 14, N'Sách', N'tai nghe test', N'test', N'test lần 10', N'test', 1, 0, 0, NULL, NULL, CAST(N'2025-04-25' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (12, 14, N'Laptop', N'tai nghe test', N'test', N'test lần 11', N'https://laptopdell.com.vn/wp-content/uploads/2022/07/laptop_lenovo_legion_s7_8.jpg', 0, 0, 1, NULL, CAST(10000000.00 AS Decimal(10, 2)), CAST(N'2025-04-25' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (13, 14, N'Laptop', N'tai nghe test', N'test', N'test lần 12', N'https://laptopdell.com.vn/wp-content/uploads/2022/07/laptop_lenovo_legion_s7_8.jpg', 1, 0, 0, NULL, NULL, CAST(N'2025-04-25' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (16, 2, N'Bàn phím', N'sản phẩm test', N'hehehe', N'hehehehhhhhhh', N'https://d1hjkbq40fs2x4.cloudfront.net/2016-01-31/files/1045.jpg', 0, 1, 1, CAST(1000.00 AS Decimal(10, 2)), CAST(1000.00 AS Decimal(10, 2)), CAST(N'2025-04-28' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (17, 2, N'Sách', N'tai nghe test', N'test', N'test so chính sửa', N'https://cdn-media.sforum.vn/storage/app/media/anh-dep-16.jpg', 0, 1, 1, CAST(100.00 AS Decimal(10, 2)), CAST(10000.00 AS Decimal(10, 2)), CAST(N'2025-04-28' AS Date))
INSERT [dbo].[items] ([item_id], [student_id], [category_name], [itemName], [title], [description], [image_url], [is_for_giveaway], [is_for_rent], [is_for_sale], [rent_price], [sale_price], [created_at]) VALUES (18, 2, N'Laptop', N'tai nghe test', N'test', N'chinnhr sửa', N'https://cdn-media.sforum.vn/storage/app/media/anh-dep-16.jpg', 1, 0, 0, CAST(0.00 AS Decimal(10, 2)), CAST(0.00 AS Decimal(10, 2)), CAST(N'2025-04-28' AS Date))
SET IDENTITY_INSERT [dbo].[items] OFF
GO
SET IDENTITY_INSERT [dbo].[messages] ON 

INSERT [dbo].[messages] ([message_id], [sender_id], [receiver_id], [content], [created_at]) VALUES (1, 2, 1, N'Chào bạn.', CAST(N'2024-10-11' AS Date))
INSERT [dbo].[messages] ([message_id], [sender_id], [receiver_id], [content], [created_at]) VALUES (2, 1, 2, N'Mình ổn.', CAST(N'2024-06-15' AS Date))
INSERT [dbo].[messages] ([message_id], [sender_id], [receiver_id], [content], [created_at]) VALUES (3, 3, 1, N'Bạn khỏe không?', CAST(N'2024-12-29' AS Date))
INSERT [dbo].[messages] ([message_id], [sender_id], [receiver_id], [content], [created_at]) VALUES (4, 1, 3, N'Khỏe bạn.', CAST(N'2023-07-11' AS Date))
SET IDENTITY_INSERT [dbo].[messages] OFF
GO
SET IDENTITY_INSERT [dbo].[students] ON 

INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (1, N'admin@fpt.edu.vn', N'123456', N'Admin', N'https://www.facebook.com/profile.php?id=100024546781234')
INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (2, N'user1@fpt.edu.vn', N'pass1', N'Người dùng Một', N'https://www.facebook.com/profile.php?id=100024546781234')
INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (3, N'user2@fpt.edu.vn', N'pass2', N'Người dùng Hai', N'https://www.facebook.com/profile.php?id=100024546781234')
INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (6, N'test@fpt.edu.vn', N'123456', N'Trần Hoàng Dũng', N'https://www.facebook.com/profile.php?id=100024546781234')
INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (7, N'test1@fpt.edu.vn', N'123456', N'Trần Hoàng Dũng', N'https://www.facebook.com/profile.php?id=100024546781234')
INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (8, N'test2@fpt.edu.vn', N'123456', N'Trần Hoàng Dũng', N'https://www.facebook.com/profile.php?id=100024546781234')
INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (9, N'test3@fpt.edu.vn', N'123456', N'Trần Hoàng Dũng', N'https://www.facebook.com/profile.php?id=100024546781234')
INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (10, N'test4@fpt.edu.vn', N'123456', N'Trần Hoàng Dũng', N'https://www.facebook.com/profile.php?id=100024546781234')
INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (11, N'test5@fpt.edu.vn', N'123456', N'Trần Hoàng Dũng', N'https://www.facebook.com/profile.php?id=100024546781234')
INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (12, N'test6@fpt.edu.vn', N'123456', N'Trần Hoàng Dũng', N'https://www.facebook.com/profile.php?id=100024546781234')
INSERT [dbo].[students] ([student_id], [email], [password], [full_name], [facebook_url]) VALUES (14, N'test10@fpt.edu.vn', N'123456', N'Trần Hoàng Dũng', N'https://www.facebook.com/')
SET IDENTITY_INSERT [dbo].[students] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__students__AB6E6164796229BB]    Script Date: 06/05/2025 6:18:13 CH ******/
ALTER TABLE [dbo].[students] ADD UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UQ__students__AB6E6164F6B73EA7]    Script Date: 06/05/2025 6:18:13 CH ******/
ALTER TABLE [dbo].[students] ADD UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[items] ADD  DEFAULT ((0)) FOR [is_for_giveaway]
GO
ALTER TABLE [dbo].[items] ADD  DEFAULT ((0)) FOR [is_for_rent]
GO
ALTER TABLE [dbo].[items] ADD  DEFAULT ((0)) FOR [is_for_sale]
GO
ALTER TABLE [dbo].[messages] ADD  DEFAULT (getdate()) FOR [created_at]
GO
ALTER TABLE [dbo].[students] ADD  DEFAULT ('') FOR [facebook_url]
GO
ALTER TABLE [dbo].[items]  WITH CHECK ADD FOREIGN KEY([student_id])
REFERENCES [dbo].[students] ([student_id])
GO
ALTER TABLE [dbo].[items]  WITH CHECK ADD FOREIGN KEY([student_id])
REFERENCES [dbo].[students] ([student_id])
GO
ALTER TABLE [dbo].[messages]  WITH CHECK ADD FOREIGN KEY([receiver_id])
REFERENCES [dbo].[students] ([student_id])
GO
ALTER TABLE [dbo].[messages]  WITH CHECK ADD FOREIGN KEY([receiver_id])
REFERENCES [dbo].[students] ([student_id])
GO
ALTER TABLE [dbo].[messages]  WITH CHECK ADD FOREIGN KEY([sender_id])
REFERENCES [dbo].[students] ([student_id])
GO
ALTER TABLE [dbo].[messages]  WITH CHECK ADD FOREIGN KEY([sender_id])
REFERENCES [dbo].[students] ([student_id])
GO
