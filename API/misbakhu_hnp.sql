-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 06, 2018 at 12:14 PM
-- Server version: 5.6.38-log
-- PHP Version: 5.6.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `misbakhu_hnp`
--

-- --------------------------------------------------------

--
-- Table structure for table `tb_data`
--

CREATE TABLE `tb_data` (
  `kode` varchar(4) NOT NULL,
  `gejala` varchar(150) NOT NULL,
  `P001` varchar(5) NOT NULL,
  `P002` varchar(5) NOT NULL,
  `P003` varchar(5) NOT NULL,
  `P004` varchar(5) NOT NULL,
  `densitas` double NOT NULL,
  `plausibility` double NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_data`
--

INSERT INTO `tb_data` (`kode`, `gejala`, `P001`, `P002`, `P003`, `P004`, `densitas`, `plausibility`) VALUES
('G001', 'Batuk keras', 'P001,', 'P002', '', '', 0.8, 0.4),
('G002', 'Otot paha dan kaki terasa lemah', 'P001', '', '', '', 0.6, 0.4),
('G003', 'Nyeri atau kesemutan dibagian pinggang', 'P001', '', '', '', 0.7, 0.3),
('G004', 'Nyeri saat membungkuk', 'P001,', 'P002,', 'P003,', 'P004', 0.7, 0.3),
('G005', 'Nyeri saat memutar badan', 'P001,', 'P002,', 'P003,', 'P004', 0.8, 0.2),
('G006', 'Nyeri atau kesemutan pada bagian depan paha', 'P001', '', '', '', 0.7, 0.3),
('G007', 'Nyeri atau kesemutan pada bagian paha samping depan', 'P001', '', '', '', 0.7, 0.3),
('G008', 'Nyeri atau kesemutan pada bagian paha samping luar', 'P001', '', '', '', 0.7, 0.3),
('G009', 'Nyeri atau kesemutan pada bagian paha belakang', 'P001,', '', 'P003', '', 0.7, 0.3),
('G010', 'Nyeri atau kesemutan pada bagian pantat', 'P001,', '', 'P003', '', 0.6, 0.4),
('G011', 'Nyeri pada betis belakang sampai tumit dan telapak kaki', 'P001', '', '', '', 0.4, 0.6),
('G012', 'Tidak kuat duduk terlalu lama', 'P001,', 'P002,', 'P003,', 'P004', 0.6, 0.4),
('G013', 'Tidak kuat berdiri terlalu lama', 'P001,', 'P002,', 'P003,', 'P004', 0.6, 0.4),
('G014', 'Kesulitan mengontrol buang air besar atau air kecil', 'P001,', '', 'P003', '', 0.4, 0.6),
('G015', 'Kaki kesemutan', 'P001,', '', 'P003', '', 0.5, 0.5),
('G016', 'Nyeri saat menggerakkan leher', '', 'P002,', 'P003,', 'P004', 0.6, 0.4),
('G017', 'Nyeri atau kesemutan didekat telinga', '', 'P002,', '', 'P004', 0.6, 0.4),
('G018', 'Nyeri atau kesemutan di daerah tulang belikat', '', 'P002,', 'P003,', 'P004', 0.4, 0.6),
('G019', 'Nyeri menjalar ke arah bahu, lengan, jari', '', 'P002,', 'P003,', 'P004', 0.8, 0.2),
('G020', 'Kelumpuhan lengan', '', 'P002', '', '', 0.4, 0.6);

-- --------------------------------------------------------

--
-- Table structure for table `tb_diagnosa`
--

CREATE TABLE `tb_diagnosa` (
  `recid` int(11) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `umur` int(11) NOT NULL,
  `diagnosa` varchar(50) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tb_diagnosa`
--

INSERT INTO `tb_diagnosa` (`recid`, `nama`, `umur`, `diagnosa`) VALUES
(1, 'Ronaldo', 24, 'HNP Lumbal'),
(2, 'Bale', 24, 'HNP Servikal'),
(3, 'Bale', 24, 'Nyeri Punggung Biasa'),
(4, 'Bale', 24, 'Nyeri Leher Biasa'),
(5, 'Bale', 24, 'Nyeri Leher Biasa'),
(6, 'Ronaldo', 21, 'HNP Servikal'),
(10, 'doni', 26, 'HNP Lumbal'),
(14, 'catur', 21, 'HNP Lumbal'),
(11, 'dedi', 32, 'HNP Lumbal'),
(12, 'yy', 23, 'HNP Lumbal'),
(13, 'tes', 25, 'HNP Lumbal'),
(15, 'putra', 21, 'HNP Lumbal'),
(16, 'okti', 30, 'HNP Lumbal'),
(17, 'cay', 46, 'HNP Lumbal'),
(18, 'trz', 28, 'HNP Lumbal'),
(19, 'yy', 66, 'HNP Lumbal'),
(20, 'tt', 66, 'HNP Lumbal'),
(21, '66', 65, 'HNP Lumbal'),
(22, 'jtt', 69, 'HNP Lumbal'),
(23, 'catut', 50, 'HNP Lumbal'),
(24, 'oke', 23, 'HNP Lumbal'),
(25, 'oye', 36, 'HNP Servikal'),
(26, 'yee', 80, 'HNP Lumbal');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_data`
--
ALTER TABLE `tb_data`
  ADD UNIQUE KEY `kode` (`kode`);

--
-- Indexes for table `tb_diagnosa`
--
ALTER TABLE `tb_diagnosa`
  ADD PRIMARY KEY (`recid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_diagnosa`
--
ALTER TABLE `tb_diagnosa`
  MODIFY `recid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
