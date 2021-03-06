USE [goods]
GO
/****** Object:  Table [dbo].[t_user]    Script Date: 12/29/2014 15:42:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_user](
	[uid] [char](32) NOT NULL,
	[loginname] [varchar](50) NULL,
	[loginpass] [varchar](max) NULL,
	[email] [varchar](50) NULL,
	[status] [int] NULL,
	[activationCode] [char](64) NULL,
 CONSTRAINT [PK__t_user__DD7012645CD6CB2B] PRIMARY KEY CLUSTERED 
(
	[uid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[t_category]    Script Date: 12/29/2014 15:42:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_category](
	[cid] [char](32) NOT NULL,
	[cname] [varchar](50) NULL,
	[pid] [char](32) NULL,
	[des] [varchar](100) NULL,
	[orderBy] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK__t_catego__D837D05F6E01572D] PRIMARY KEY CLUSTERED 
(
	[cid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[t_admin]    Script Date: 12/29/2014 15:42:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_admin](
	[adminId] [char](32) NOT NULL,
	[adminname] [varchar](50) NULL,
	[adminpwd] [varchar](max) NULL,
	[authority] [int] NULL,
	[email] [varchar](50) NULL,
	[status] [int] NULL,
	[activationCode] [char](64) NULL,
 CONSTRAINT [PK__t_admin__AD0500A6440B1D61] PRIMARY KEY CLUSTERED 
(
	[adminId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[t_book]    Script Date: 12/29/2014 15:42:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_book](
	[bid] [char](32) NOT NULL,
	[bname] [varchar](200) NULL,
	[author] [varchar](50) NULL,
	[price] [decimal](8, 2) NULL,
	[currPrice] [decimal](8, 2) NULL,
	[discount] [decimal](3, 1) NULL,
	[press] [varchar](100) NULL,
	[publishtime] [char](10) NULL,
	[edition] [int] NULL,
	[pageNum] [int] NULL,
	[wordNum] [int] NULL,
	[printtime] [char](10) NULL,
	[booksize] [int] NULL,
	[paper] [varchar](50) NULL,
	[adminId] [varchar](50) NULL,
	[cid] [char](32) NOT NULL,
	[image_w] [varchar](100) NULL,
	[image_b] [varchar](100) NULL,
	[orderBy] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK__t_book__DE90ADE749C3F6B7] PRIMARY KEY CLUSTERED 
(
	[bid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[t_order]    Script Date: 12/29/2014 15:42:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_order](
	[oid] [char](32) NOT NULL,
	[ordertime] [char](19) NULL,
	[total] [decimal](10, 2) NULL,
	[status] [int] NULL,
	[address] [varchar](1000) NULL,
	[adminId] [char](32) NULL,
	[uid] [char](32) NULL,
	[orderBy] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK__t_order__C2FFCF1375A278F5] PRIMARY KEY CLUSTERED 
(
	[oid] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[t_orderitem]    Script Date: 12/29/2014 15:42:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_orderitem](
	[orderItemId] [char](32) NOT NULL,
	[quantity] [int] NULL,
	[subtotal] [decimal](8, 2) NULL,
	[bid] [char](32) NULL,
	[oid] [char](32) NULL,
	[orderBy] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK__t_orderi__3724BD527F2BE32F] PRIMARY KEY CLUSTERED 
(
	[orderItemId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[t_cartitem]    Script Date: 12/29/2014 15:42:53 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[t_cartitem](
	[cartItemId] [char](32) NOT NULL,
	[quantity] [int] NULL,
	[bid] [char](32) NULL,
	[uid] [char](32) NULL,
	[orderBy] [int] IDENTITY(1,1) NOT NULL,
 CONSTRAINT [PK__t_cartit__283983B6656C112C] PRIMARY KEY CLUSTERED 
(
	[cartItemId] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Default [DF__t_admin__adminna__45F365D3]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_admin] ADD  CONSTRAINT [DF__t_admin__adminna__45F365D3]  DEFAULT (NULL) FOR [adminname]
GO
/****** Object:  Default [DF__t_admin__adminpw__46E78A0C]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_admin] ADD  CONSTRAINT [DF__t_admin__adminpw__46E78A0C]  DEFAULT (NULL) FOR [adminpwd]
GO
/****** Object:  Default [DF__t_book__bname__4BAC3F29]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__bname__4BAC3F29]  DEFAULT (NULL) FOR [bname]
GO
/****** Object:  Default [DF__t_book__author__4CA06362]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__author__4CA06362]  DEFAULT (NULL) FOR [author]
GO
/****** Object:  Default [DF__t_book__price__4D94879B]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__price__4D94879B]  DEFAULT (NULL) FOR [price]
GO
/****** Object:  Default [DF__t_book__currPric__4E88ABD4]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__currPric__4E88ABD4]  DEFAULT (NULL) FOR [currPrice]
GO
/****** Object:  Default [DF__t_book__discount__4F7CD00D]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__discount__4F7CD00D]  DEFAULT (NULL) FOR [discount]
GO
/****** Object:  Default [DF__t_book__press__5070F446]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__press__5070F446]  DEFAULT (NULL) FOR [press]
GO
/****** Object:  Default [DF__t_book__publisht__5165187F]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__publisht__5165187F]  DEFAULT (NULL) FOR [publishtime]
GO
/****** Object:  Default [DF__t_book__edition__52593CB8]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__edition__52593CB8]  DEFAULT (NULL) FOR [edition]
GO
/****** Object:  Default [DF__t_book__pageNum__534D60F1]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__pageNum__534D60F1]  DEFAULT (NULL) FOR [pageNum]
GO
/****** Object:  Default [DF__t_book__wordNum__5441852A]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__wordNum__5441852A]  DEFAULT (NULL) FOR [wordNum]
GO
/****** Object:  Default [DF__t_book__printtim__5535A963]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__printtim__5535A963]  DEFAULT (NULL) FOR [printtime]
GO
/****** Object:  Default [DF__t_book__booksize__5629CD9C]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__booksize__5629CD9C]  DEFAULT (NULL) FOR [booksize]
GO
/****** Object:  Default [DF__t_book__paper__571DF1D5]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__paper__571DF1D5]  DEFAULT (NULL) FOR [paper]
GO
/****** Object:  Default [DF__t_book__cid__5812160E]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__cid__5812160E]  DEFAULT (NULL) FOR [cid]
GO
/****** Object:  Default [DF__t_book__image_w__59063A47]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__image_w__59063A47]  DEFAULT (NULL) FOR [image_w]
GO
/****** Object:  Default [DF__t_book__image_b__59FA5E80]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book] ADD  CONSTRAINT [DF__t_book__image_b__59FA5E80]  DEFAULT (NULL) FOR [image_b]
GO
/****** Object:  Default [DF__t_cartite__quant__6754599E]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_cartitem] ADD  CONSTRAINT [DF__t_cartite__quant__6754599E]  DEFAULT (NULL) FOR [quantity]
GO
/****** Object:  Default [DF__t_cartitem__bid__68487DD7]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_cartitem] ADD  CONSTRAINT [DF__t_cartitem__bid__68487DD7]  DEFAULT (NULL) FOR [bid]
GO
/****** Object:  Default [DF__t_cartitem__uid__693CA210]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_cartitem] ADD  CONSTRAINT [DF__t_cartitem__uid__693CA210]  DEFAULT (NULL) FOR [uid]
GO
/****** Object:  Default [DF__t_categor__cname__6FE99F9F]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_category] ADD  CONSTRAINT [DF__t_categor__cname__6FE99F9F]  DEFAULT (NULL) FOR [cname]
GO
/****** Object:  Default [DF__t_category__pid__70DDC3D8]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_category] ADD  CONSTRAINT [DF__t_category__pid__70DDC3D8]  DEFAULT (NULL) FOR [pid]
GO
/****** Object:  Default [DF__t_category__des__71D1E811]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_category] ADD  CONSTRAINT [DF__t_category__des__71D1E811]  DEFAULT (NULL) FOR [des]
GO
/****** Object:  Default [DF__t_order__orderti__778AC167]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_order] ADD  CONSTRAINT [DF__t_order__orderti__778AC167]  DEFAULT (NULL) FOR [ordertime]
GO
/****** Object:  Default [DF__t_order__total__787EE5A0]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_order] ADD  CONSTRAINT [DF__t_order__total__787EE5A0]  DEFAULT (NULL) FOR [total]
GO
/****** Object:  Default [DF__t_order__status__797309D9]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_order] ADD  CONSTRAINT [DF__t_order__status__797309D9]  DEFAULT (NULL) FOR [status]
GO
/****** Object:  Default [DF__t_order__address__7A672E12]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_order] ADD  CONSTRAINT [DF__t_order__address__7A672E12]  DEFAULT (NULL) FOR [address]
GO
/****** Object:  Default [DF__t_order__uid__7B5B524B]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_order] ADD  CONSTRAINT [DF__t_order__uid__7B5B524B]  DEFAULT (NULL) FOR [uid]
GO
/****** Object:  Default [DF__t_orderit__quant__01142BA1]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_orderitem] ADD  CONSTRAINT [DF__t_orderit__quant__01142BA1]  DEFAULT (NULL) FOR [quantity]
GO
/****** Object:  Default [DF__t_orderit__subto__02084FDA]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_orderitem] ADD  CONSTRAINT [DF__t_orderit__subto__02084FDA]  DEFAULT (NULL) FOR [subtotal]
GO
/****** Object:  Default [DF__t_orderitem__bid__02FC7413]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_orderitem] ADD  CONSTRAINT [DF__t_orderitem__bid__02FC7413]  DEFAULT (NULL) FOR [bid]
GO
/****** Object:  Default [DF__t_orderitem__oid__06CD04F7]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_orderitem] ADD  CONSTRAINT [DF__t_orderitem__oid__06CD04F7]  DEFAULT (NULL) FOR [oid]
GO
/****** Object:  Default [DF__t_user__loginnam__5EBF139D]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_user] ADD  CONSTRAINT [DF__t_user__loginnam__5EBF139D]  DEFAULT (NULL) FOR [loginname]
GO
/****** Object:  Default [DF__t_user__loginpas__5FB337D6]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_user] ADD  CONSTRAINT [DF__t_user__loginpas__5FB337D6]  DEFAULT (NULL) FOR [loginpass]
GO
/****** Object:  Default [DF__t_user__email__60A75C0F]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_user] ADD  CONSTRAINT [DF__t_user__email__60A75C0F]  DEFAULT (NULL) FOR [email]
GO
/****** Object:  Default [DF__t_user__status__619B8048]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_user] ADD  CONSTRAINT [DF__t_user__status__619B8048]  DEFAULT (NULL) FOR [status]
GO
/****** Object:  Default [DF__t_user__activati__628FA481]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_user] ADD  CONSTRAINT [DF__t_user__activati__628FA481]  DEFAULT (NULL) FOR [activationCode]
GO
/****** Object:  ForeignKey [FK_t_book_t_book]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book]  WITH CHECK ADD  CONSTRAINT [FK_t_book_t_book] FOREIGN KEY([bid])
REFERENCES [dbo].[t_book] ([bid])
GO
ALTER TABLE [dbo].[t_book] CHECK CONSTRAINT [FK_t_book_t_book]
GO
/****** Object:  ForeignKey [FK_t_book_t_category]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_book]  WITH CHECK ADD  CONSTRAINT [FK_t_book_t_category] FOREIGN KEY([cid])
REFERENCES [dbo].[t_category] ([cid])
GO
ALTER TABLE [dbo].[t_book] CHECK CONSTRAINT [FK_t_book_t_category]
GO
/****** Object:  ForeignKey [FK_t_cartitem_t_book]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_cartitem]  WITH CHECK ADD  CONSTRAINT [FK_t_cartitem_t_book] FOREIGN KEY([bid])
REFERENCES [dbo].[t_book] ([bid])
GO
ALTER TABLE [dbo].[t_cartitem] CHECK CONSTRAINT [FK_t_cartitem_t_book]
GO
/****** Object:  ForeignKey [FK_t_cartitem_t_user]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_cartitem]  WITH CHECK ADD  CONSTRAINT [FK_t_cartitem_t_user] FOREIGN KEY([uid])
REFERENCES [dbo].[t_user] ([uid])
GO
ALTER TABLE [dbo].[t_cartitem] CHECK CONSTRAINT [FK_t_cartitem_t_user]
GO
/****** Object:  ForeignKey [FK_t_category_t_category]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_category]  WITH CHECK ADD  CONSTRAINT [FK_t_category_t_category] FOREIGN KEY([pid])
REFERENCES [dbo].[t_category] ([cid])
GO
ALTER TABLE [dbo].[t_category] CHECK CONSTRAINT [FK_t_category_t_category]
GO
/****** Object:  ForeignKey [FKB9AF3069B786AF73]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_category]  WITH CHECK ADD  CONSTRAINT [FKB9AF3069B786AF73] FOREIGN KEY([cid])
REFERENCES [dbo].[t_category] ([cid])
GO
ALTER TABLE [dbo].[t_category] CHECK CONSTRAINT [FKB9AF3069B786AF73]
GO
/****** Object:  ForeignKey [FK_t_order_t_admin]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_order]  WITH CHECK ADD  CONSTRAINT [FK_t_order_t_admin] FOREIGN KEY([adminId])
REFERENCES [dbo].[t_admin] ([adminId])
GO
ALTER TABLE [dbo].[t_order] CHECK CONSTRAINT [FK_t_order_t_admin]
GO
/****** Object:  ForeignKey [FK_t_order_t_user]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_order]  WITH CHECK ADD  CONSTRAINT [FK_t_order_t_user] FOREIGN KEY([uid])
REFERENCES [dbo].[t_user] ([uid])
GO
ALTER TABLE [dbo].[t_order] CHECK CONSTRAINT [FK_t_order_t_user]
GO
/****** Object:  ForeignKey [FK_t_orderitem_t_order]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_orderitem]  WITH CHECK ADD  CONSTRAINT [FK_t_orderitem_t_order] FOREIGN KEY([oid])
REFERENCES [dbo].[t_order] ([oid])
GO
ALTER TABLE [dbo].[t_orderitem] CHECK CONSTRAINT [FK_t_orderitem_t_order]
GO
/****** Object:  ForeignKey [FK794A55699259D1D]    Script Date: 12/29/2014 15:42:53 ******/
ALTER TABLE [dbo].[t_orderitem]  WITH CHECK ADD  CONSTRAINT [FK794A55699259D1D] FOREIGN KEY([bid])
REFERENCES [dbo].[t_book] ([bid])
GO
ALTER TABLE [dbo].[t_orderitem] CHECK CONSTRAINT [FK794A55699259D1D]
GO
