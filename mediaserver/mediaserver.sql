-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: May 21, 2012 at 06:21 PM
-- Server version: 5.5.22
-- PHP Version: 5.3.10-1ubuntu3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `mediaserver`
--
CREATE DATABASE `mediaserver` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `mediaserver`;

-- --------------------------------------------------------

--
-- Table structure for table `channels`
--

CREATE TABLE IF NOT EXISTS `channels` (
  `id` varchar(50) NOT NULL,
  `name` varchar(50) NOT NULL,
  `icon` varchar(256) NOT NULL,
  `uri` varchar(512) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `channels`
--

INSERT INTO `channels` (`id`, `name`, `icon`, `uri`) VALUES
('animalplanet_sk', 'Animal Planet', 'http://www.lyngsat-logo.com/logo/tv/aa/animal_planet_europe.jpg', ''),
('axn_sk', 'AXN', 'http://www.lyngsat-logo.com/logo/tv/aa/axn_cz.jpg', ''),
('axncrime_sk', 'AXN Crime', 'http://www.lyngsat-logo.com/logo/tv/aa/axn_crime.jpg', ''),
('axndigi_sk', 'AXN Digi TV', 'http://www.lyngsat-logo.com/logo/tv/aa/axn_cz.jpg', ''),
('axnscifi_sk', 'AXN Sci-fi', 'http://www.lyngsat-logo.com/logo/tv/aa/axn_scifi.jpg', ''),
('ducktv_sk', 'duck.tv', 'http://www.lyngsat-logo.com/logo/tv/dd/duck_tv.jpg', ''),
('ducktvhd_sk', 'duck.tv HD', 'http://www.lyngsat-logo.com/logo/tv/dd/duck_tv_hd.jpg', ''),
('cartnet_sk', 'Cartoon Network', 'http://www.lyngsat-logo.com/logo/tv/cc/cartoon_network_uk.jpg', ''),
('cartnetupc_sk', 'Cartoon Network UPC', 'http://www.lyngsat-logo.com/logo/tv/cc/cartoon_network_uk.jpg', ''),
('cinemax_sk', 'Cinemax', 'http://www.lyngsat-logo.com/logo/tv/cc/cinemax_ce.jpg', ''),
('cinemax2_sk', 'Cinemax2', 'http://www.lyngsat-logo.com/logo/tv/cc/cinemax2_ce.jpg', ''),
('csfilm_sk', 'CS Film', 'http://www.lyngsat-logo.com/logo/tv/cc/cs_film.jpg', ''),
('ct1_sk', 'CT1', 'http://www.lyngsat-logo.com/logo/tv/cc/ceska_televize1.jpg', ''),
('ct2_sk', 'CT2', 'http://www.lyngsat-logo.com/logo/tv/cc/ceska_televize2.jpg', ''),
('ct24_sk', 'CT24', 'http://www.lyngsat-logo.com/logo/tv/cc/ceska_televize24.jpg', ''),
('ct4_sk', 'CT4', 'http://www.lyngsat-logo.com/logo/tv/cc/ceska_televize4.jpg', ''),
('discovery_sk', 'Discovery', 'http://www.lyngsat-logo.com/logo/tv/dd/discovery_europe.jpg', ''),
('discoverydigitv_sk', 'Discovery Digi TV', 'http://www.lyngsat-logo.com/logo/tv/dd/discovery_ro.jpg', ''),
('discoveryen_sk', 'Discovery EN', 'http://www.lyngsat-logo.com/logo/tv/dd/discovery_europe.jpg', ''),
('discoveryhd_sk', 'Discovery HD', 'http://www.lyngsat-logo.com/logo/tv/dd/discovery_hd_de.jpg', ''),
('discoveryinvestigation_sk', 'Discovery Investigation', 'http://www.lyngsat-logo.com/logo/tv/ii/investigation_discovery_us.jpg', ''),
('discoveryscience_sk', 'Discovery Science', 'http://www.lyngsat-logo.com/logo/tv/dd/discovery_science_uk.jpg', ''),
('discoverytravelandliving_sk', 'Discovery Travel and Living', 'http://www.lyngsat-logo.com/logo/tv/dd/discovery_travel_living.jpg', ''),
('discoveryworld_sk', 'Discovery World', 'http://www.lyngsat-logo.com/logo/tv/dd/discovery_world.jpg', ''),
('disneychannel_sk', 'Disney Channel', 'http://www.lyngsat-logo.com/logo/tv/dd/disney_channel_hu_cz.jpg', ''),
('doma_sk', 'Doma', 'http://www.lyngsat-logo.com/logo/tv/dd/doma_tv.jpg', ''),
('doq_sk', 'DoQ', 'http://www.lyngsat-logo.com/logo/tv/dd/doq.jpg', ''),
('sport1de_sk', 'Sport1 DE', 'http://www.lyngsat-logo.com/logo/tv/ss/sport1_de.jpg', ''),
('dvojka_sk', 'Dvojka', 'http://www.lyngsat-logo.com/logo/tv/ss/stv_dvojka.jpg', ''),
('duna_sk', 'Duna', 'http://www.lyngsat-logo.com/logo/tv/dd/duna_tv.jpg', ''),
('eurosport1_sk', 'Eurosport 1', 'http://www.lyngsat-logo.com/logo/tv/ee/eurosport.jpg', ''),
('eurosport2_sk', 'Eurosport 2', 'http://www.lyngsat-logo.com/logo/tv/ee/eurosport2_ne.jpg', ''),
('filmplus_sk', 'Film Plus', 'http://www.lyngsat-logo.com/logo/tv/ff/film_plus_cz.jpg', ''),
('filmbox_sk', 'Filmbox', 'http://www.lyngsat-logo.com/logo/tv/ff/filmbox.jpg', ''),
('filmboxhd_sk', 'Filmbox HD', 'http://www.lyngsat-logo.com/logo/tv/ff/filmbox_hd.jpg', ''),
('filmboxextra_sk', 'Filmbox extra', 'http://www.lyngsat-logo.com/logo/tv/ff/filmbox_extra.jpg', ''),
('universalchannel_sk', 'Universal Channel', 'http://www.lyngsat-logo.com/logo/tv/uu/universal_channel.jpg', ''),
('hbo_sk', 'HBO', 'http://www.lyngsat-logo.com/logo/tv/hh/hbo_cz.jpg', ''),
('hbo2_sk', 'HBO2', 'http://www.lyngsat-logo.com/logo/tv/hh/hbo2_ce.jpg', ''),
('hbocomedy_sk', 'HBO Comedy', 'http://www.lyngsat-logo.com/logo/tv/hh/hbo_comedy_ceu.jpg', ''),
('thehistorychannel_sk', 'History Channel', 'http://www.lyngsat-logo.com/logo/tv/hh/history_europe.jpg', ''),
('thehistorychannelhd_sk', 'History Channel HD', 'http://www.lyngsat-logo.com/logo/tv/hh/history_hd.jpg', ''),
('jednotka_sk', 'Jednotka', 'http://www.lyngsat-logo.com/logo/tv/ss/stv_jednotka.jpg', 'http://danman.eu/mediaserver/stream.php?ch=83'),
('jimjam_sk', 'Jim Jam', 'http://www.lyngsat-logo.com/logo/tv/jj/jim_jam.jpg', ''),
('joj_sk', 'JOJ', 'http://www.lyngsat-logo.com/logo/tv/jj/joj_tv.jpg', 'http://danman.eu/mediaserver/stream.php?ch=2'),
('jojplus_sk', 'JOJ Plus', 'http://www.lyngsat-logo.com/logo/tv/jj/joj_plus.jpg', ''),
('kinocs_sk', 'Kino CS', 'http://www.lyngsat-logo.com/logo/tv/kk/kino_cs.jpg', ''),
('leo_sk', 'Leo', 'http://www.lyngsat-logo.com/logo/tv/ll/leo_tv.jpg', ''),
('liptov_sk', 'Liptov', 'http://www.lyngsat-logo.com/logo/tv/ll/liptov_tv.jpg', ''),
('lux_sk', 'Lux', 'http://www.lyngsat-logo.com/logo/tv/ll/lux_televizia.jpg', ''),
('m1_sk', 'M1', 'http://www.lyngsat-logo.com/logo/tv/mm/magyar_tv1.jpg', ''),
('markiza_sk', 'Markiza', 'http://www.lyngsat-logo.com/logo/tv/mm/markiza.jpg', 'http://danman.eu/mediaserver/stream.php?ch=1'),
('mgm_sk', 'MGM', 'http://www.lyngsat-logo.com/logo/tv/mm/mgm_channel_ce.jpg', ''),
('minimax_sk', 'Minimax', 'http://www.lyngsat-logo.com/logo/tv/mm/minimax_cz.jpg', ''),
('mtv_sk', 'MTV', 'http://www.lyngsat-logo.com/logo/tv/mm/mtv_cz.jpg', ''),
('musiq1_sk', 'Musiq1', 'http://www.lyngsat-logo.com/logo/tv/mm/musiq1.jpg', ''),
('nationalgeographic_sk', 'National Geographic', 'http://www.lyngsat-logo.com/logo/tv/nn/nat_geo_hu.jpg', ''),
('nationalgeographicwild_sk', 'National Geographic Wild', 'http://www.lyngsat-logo.com/logo/tv/nn/nat_geo_wild_emea.jpg', ''),
('nova_sk', 'Nova', 'http://www.lyngsat-logo.com/logo/tv/nn/nova_tv_cz.jpg', ''),
('novacinema_sk', 'Nova Cinema', 'http://www.lyngsat-logo.com/logo/tv/nn/nova_cinema.jpg', ''),
('novasport_sk', 'Nova sport', 'http://www.lyngsat-logo.com/logo/tv/nn/nova_sport_cz.jpg', ''),
('ocko_sk', 'Ocko', 'http://www.lyngsat-logo.com/logo/tv/oo/ocko.jpg', ''),
('prima_sk', 'Prima', 'http://www.lyngsat-logo.com/logo/tv/pp/prima_tv_cz.jpg', ''),
('primacool_sk', 'Prima Cool', 'http://www.lyngsat-logo.com/logo/tv/pp/prima_cool.jpg', ''),
('privatespice_sk', 'Private Spice', 'http://www.lyngsat-logo.com/logo/tv/ss/spice_private.jpg', ''),
('spektrum_sk', 'Spektrum', 'http://www.lyngsat-logo.com/logo/tv/ss/spektrum.jpg', ''),
('sport1_sk', 'Sport1', 'http://www.lyngsat-logo.com/logo/tv/ss/sport1.jpg', ''),
('sport2_sk', 'Sport2', 'http://www.lyngsat-logo.com/logo/tv/ss/sport2.jpg', ''),
('sport5_sk', 'Sport 5', 'http://www.lyngsat-logo.com/logo/tv/ss/sport5_cz.jpg', ''),
('ta3_sk', 'TA3', 'http://www.lyngsat-logo.com/logo/tv/tt/ta3_news.jpg', 'rtsp://stream.the.sk/live/ta3.3gp'),
('thefishingandhunting_sk', 'The Fishing and Hunting', 'http://www.lyngsat-logo.com/logo/tv/ff/fishing_and_hunting.jpg', ''),
('viasatnaturehd_sk', 'Viasat Nature HD', 'http://www.lyngsat-logo.com/logo/tv/vv/viasat_nature_hd.jpg', ''),
('tvbarrandov_sk', 'TV Barrandov', 'http://www.lyngsat-logo.com/logo/tv/bb/barrandov_tv.jpg', ''),
('tvnoe_sk', 'TV Noe', 'http://www.lyngsat-logo.com/logo/tv/tt/tv_noe.jpg', ''),
('tvpaprika_sk', 'TV Paprika', 'http://www.lyngsat-logo.com/logo/tv/tt/tv_paprika_hu.jpg', ''),
('viasatexplorer_sk', 'Viasat Explorer', 'http://www.lyngsat-logo.com/logo/tv/vv/viasat_explorer.jpg', ''),
('viasathistory_sk', 'Viasat History', 'http://www.lyngsat-logo.com/logo/tv/vv/viasat_history.jpg', ''),
('daringtv_sk', 'Daring TV', 'http://www.lyngsat-logo.com/logo/tv/dd/daring_tv.jpg', ''),
('travelchannelhd_sk', 'Travel Channel HD', 'http://www.lyngsat-logo.com/logo/tv/tt/travel_channel_cz_hd.jpg', ''),
('zonereality_sk', 'Zone Reality', 'http://www.lyngsat-logo.com/logo/tv/zz/zone_reality_europe.jpg', ''),
('zoneromantica_sk', 'Zone Romantica', 'http://www.lyngsat-logo.com/logo/tv/zz/zone_romantica_polska_magyar.jpg', ''),
('viasatnature_sk', 'Viasat Nature', 'http://www.lyngsat-logo.com/logo/tv/vv/viasat_nature_east.jpg', ''),
('rtl_sk', 'RTL', 'http://www.lyngsat-logo.com/logo/tv/rr/rtl.jpg', ''),
('sat1_sk', 'SAT1', 'http://www.lyngsat-logo.com/logo/tv/ss/sat1.jpg', ''),
('espnclassic_sk', 'ESPN Classic', 'http://www.lyngsat-logo.com/logo/tv/ee/espn_classic.jpg', ''),
('extremesports_sk', 'Extreme Sports', 'http://www.lyngsat-logo.com/logo/tv/ee/extreme_sports.jpg', ''),
('espnamerica_sk', 'ESPN America', 'http://www.lyngsat-logo.com/logo/tv/ee/espn_america.jpg', ''),
('pro7_sk', 'PRO7', 'http://www.lyngsat-logo.com/logo/tv/pp/prosieben.jpg', ''),
('vox_sk', 'VOX', 'http://www.lyngsat-logo.com/logo/tv/vv/vox.jpg', ''),
('rtl2_sk', 'RTL2', 'http://www.lyngsat-logo.com/logo/tv/rr/rtl2.jpg', ''),
('kabel1_sk', 'Kabel1', 'http://www.lyngsat-logo.com/logo/tv/kk/kabel_eins.jpg', ''),
('orf1_sk', 'ORF1', 'http://www.lyngsat-logo.com/logo/tv/oo/orf1.jpg', ''),
('orf2_sk', 'ORF2', 'http://www.lyngsat-logo.com/logo/tv/oo/orf2.jpg', ''),
('superrtl_sk', 'Super RTL', 'http://www.lyngsat-logo.com/logo/tv/ss/super_rtl.jpg', ''),
('primalove_sk', 'Prima LOVE', 'http://www.lyngsat-logo.com/logo/tv/pp/prima_love_cz.jpg', ''),
('digisportsk_sk', 'Digi Sport SK', 'http://www.lyngsat-logo.com/logo/tv/dd/digi_sport_cz.jpg', ''),
('viasathistoryhd_sk', 'Viasat History HD', 'http://www.lyngsat-logo.com/logo/tv/vv/viasat_history_hd.jpg', ''),
('primafamily_sk', 'Prima Family', 'http://www.lyngsat-logo.com/logo/tv/pp/prima_family.jpg', '');

-- --------------------------------------------------------

--
-- Table structure for table `epg`
--

CREATE TABLE IF NOT EXISTS `epg` (
  `start` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `stop` varchar(25) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `channel` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `title` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `desc` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `episode_num` varchar(7) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  KEY `start` (`start`,`channel`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `epg`
--

INSERT INTO `epg` (`start`, `stop`, `channel`, `title`, `desc`, `episode_num`) VALUES
('20120521000000 +0200', '20120521030000 +0200', 'jednotka_sk', 'no schedule', 'TVxb TERMS AND CONDITIONS: This TVxb guide software may be used by private users for personal use only. Commercial use, sale, or distribution, or bundling of this software with commercial products, is prohibited. The use of TVxb software or this guide to provide a free or paid service is prohibited. The program information in this guide might be protected by copyright and distribution to other people or organizations without the permission of the copyright holders might not be permitted. [ www.tvxb.com ]', ''),
('20120521030000 +0200', '20120521043000 +0200', 'jednotka_sk', 'VedÄ¾ajÅ¡ie ÃºÄinky', 'DrÃ¡ma podÄ¾a skutoÄnÃ½ch udalostÃ­. RekonÅ¡trukcia prÃ­padu drastickÃ½ch nÃ¡sledkov pouÅ¾itia lieku thalidomid zo 60tych rokov min.storoÄia. RÃ©Å¾ia: Adolf Winkelmann ÃšÄinkujÃº: Benjamin Sadler, Katharina Wackernagel, Meyer Hans Werner (SpolkovÃ¡ republika Nemecko 2007)', ''),
('20120521043000 +0200', '20120521051500 +0200', 'jednotka_sk', 'Ushuaia - NÃ¡vrat na planÃ©tu opÃ­c, Gabun - Uganda, 2.ÄasÅ¥', 'DokumentÃ¡rny seriÃ¡l (FrancÃºzsko 2007)', ''),
('20120521051500 +0200', '20120521064000 +0200', 'jednotka_sk', 'DÃ¡msky klub', 'Radosti a starosti nielen zo sveta Å¾ien. (SlovenskÃ¡ republika 2012)', ''),
('20120521064000 +0200', '20120521072000 +0200', 'jednotka_sk', 'SprÃ¡vy STV', 'SlovenskÃ¡ republika 2012', ''),
('20120521072000 +0200', '20120521073000 +0200', 'jednotka_sk', 'GÃ³ly - body - sekundy', 'SlovenskÃ¡ republika 2012', ''),
('20120521073000 +0200', '20120521074500 +0200', 'jednotka_sk', 'PrÃ¡ca pre teba', 'Burza prÃ¡ce (SlovenskÃ¡ republika 2012)', ''),
('20120521074500 +0200', '20120521081000 +0200', 'jednotka_sk', 'Svet v obrazoch', 'RozliÄnÃ© zaujÃ­mavosti zo sveta. (SlovenskÃ¡ republika 2012)', ''),
('20120521081000 +0200', '20120521084500 +0200', 'jednotka_sk', 'Duel', 'Vedomosti premenenÃ© na peniaze. (SlovenskÃ¡ republika 2012)', ''),
('20120521084500 +0200', '20120521093000 +0200', 'jednotka_sk', '5 proti 5', 'RodinnÃ¡ zÃ¡bavnÃ¡ sÃºÅ¥aÅ¾ s moderÃ¡torom Andrejom BiÄanom, v ktorej sÃºÅ¥aÅ¾iaci hÄ¾adajÃº najÄastejÅ¡ie odpovede stovky SlovÃ¡kov na tie najjednoduchÅ¡ie otÃ¡zky (SlovenskÃ¡ republika 2012)', ''),
('20120521093000 +0200', '20120521101500 +0200', 'jednotka_sk', 'SudkyÅˆa Amy', 'NeosobnÃ¡ zaujatosÅ¥ RodinnÃ½ seriÃ¡l. ÃšÄinkujÃº: Amy BrennemanovÃ¡, Dan Futterman, Richard T.Jones, Jessica TuckovÃ¡, Marcus Giamatti, Tyne DalyovÃ¡, Karle WarrenovÃ¡ (SpojenÃ© Å¡tÃ¡ty americkÃ© 1999)', ''),
('20120521101500 +0200', '20120521110500 +0200', 'jednotka_sk', 'SudkyÅˆa Amy', 'Na prahu smrti RodinnÃ½ seriÃ¡l. ÃšÄinkujÃº: Amy BrennemanovÃ¡, Dan Futterman, Richard T.Jones, Jessica TuckovÃ¡, Marcus Giamatti, Tyne DalyovÃ¡, Karle WarrenovÃ¡ (SpojenÃ© Å¡tÃ¡ty americkÃ© 1999)', ''),
('20120521110500 +0200', '20120521120000 +0200', 'jednotka_sk', 'Sila lÃ¡sky IV', 'PokraÄovanie ÃºspeÅ¡nÃ©ho nemeckÃ©ho rom.seriÃ¡lu odohrÃ¡vajÃºcom sa v hoteli Furnstenhof. ÃšÄinkujÃº: Sarah Stork, Wolfgang Cerny, Natalie Alison, Erich Altenkopf, Dirk Galuba (SpolkovÃ¡ republika Nemecko 2008)', ''),
('20120521120000 +0200', '20120521120500 +0200', 'jednotka_sk', 'PoludÅˆajÅ¡ie sprÃ¡vy', 'SlovenskÃ¡ republika 2012', ''),
('20120521120500 +0200', '20120521132500 +0200', 'jednotka_sk', 'DÃ¡msky klub', 'Radosti a starosti nielen zo sveta Å¾ien. (SlovenskÃ¡ republika 2012)', ''),
('20120521132500 +0200', '20120521133500 +0200', 'jednotka_sk', 'PrÃ¡ca pre teba', 'Burza prÃ¡ce (SlovenskÃ¡ republika 2012)', ''),
('20120521133500 +0200', '20120521141500 +0200', 'jednotka_sk', 'Tajomstvo mojej kuchyne', 'NajlepÅ¡ie recepty Kamily MagÃ¡lovej a Å¡Ã©fkuchÃ¡ra Pavla PospÃ­Å¡ila prezradenÃ© ! (SlovenskÃ¡ republika 2012)', ''),
('20120521141500 +0200', '20120521150000 +0200', 'jednotka_sk', 'DivokÃ½ anjel', 'Telenovela. PrÃ­beh chudobnej dievÄiny Milagros /Mili/, ktorÃ¡ vyrÃ¡stla v klÃ¡Å¡tore ako sirota. ÃšÄinkujÃº: Natalia OreirovÃ¡, Facundo Arana, VerÃ³nica VieyraovÃ¡. Lydia LamaisonovÃ¡, Victoria OnettovÃ¡, Norberto DÃ­az, Mariana ArasovÃ¡, Osvaldo Guidi, Pablo Novak (ArgentÃ­na 1998)', ''),
('20120521150000 +0200', '20120521160000 +0200', 'jednotka_sk', 'BlÃ¡znivo zamilovanÃ­', 'VeÄnosÅ¥ RomantickÃ¡ komÃ©dia RÃ©Å¾ia: Riccardo Milani ÃšÄinkujÃº: Stefania Rocca, Emilio Solfrizzi, Liskova Antonia, Neri MarcorÃ© (Taliansko 2009)', ''),
('20120521160000 +0200', '20120521161500 +0200', 'jednotka_sk', 'SprÃ¡vy STV o 16.00', 'SlovenskÃ¡ republika 2012', ''),
('20120521161500 +0200', '20120521162000 +0200', 'jednotka_sk', 'GÃ³ly - body - sekundy', 'SlovenskÃ¡ republika 2012', ''),
('20120521162000 +0200', '20120521162500 +0200', 'jednotka_sk', 'PoÄasie', 'SlovenskÃ¡ republika 2012', ''),
('20120521162500 +0200', '20120521163000 +0200', 'jednotka_sk', 'PrÃ¡ca pre teba', 'Burza prÃ¡ce (SlovenskÃ¡ republika 2012)', ''),
('20120521163000 +0200', '20120521172000 +0200', 'jednotka_sk', 'Sila lÃ¡sky V', 'RomantickÃ½ seriÃ¡l. RÃ©Å¾ia: Carsten Meyer-GrohbrÃ¼gge, Connie Pfeiffer, Stefan Jonas ÃšÄinkujÃº: Uta Kargel, Lorenzo Patane, Natalie Alison, Erich Altenkopf, Antje Hagen (SpolkovÃ¡ republika Nemecko 2009)', ''),
('20120521172000 +0200', '20120521175000 +0200', 'jednotka_sk', 'Vojna kuchÃ¡rov', 'SÃºÅ¥aÅ¾no - zÃ¡bavnÃ¡ relÃ¡cia ( nielen pre kuchÃ¡rov). (SlovenskÃ¡ republika)', ''),
('20120521175000 +0200', '20120521182000 +0200', 'jednotka_sk', 'Duel', 'Vedomosti premenenÃ© na peniaze. (SlovenskÃ¡ republika 2012)', ''),
('20120521182000 +0200', '20120521190000 +0200', 'jednotka_sk', '5 proti 5', 'RodinnÃ¡ zÃ¡bavnÃ¡ sÃºÅ¥aÅ¾ s moderÃ¡torom Andrejom BiÄanom, v ktorej sÃºÅ¥aÅ¾iaci hÄ¾adajÃº najÄastejÅ¡ie odpovede stovky SlovÃ¡kov na tie najjednoduchÅ¡ie otÃ¡zky. (SlovenskÃ¡ republika 2012)', ''),
('20120521190000 +0200', '20120521194500 +0200', 'jednotka_sk', 'SprÃ¡vy STV', 'SlovenskÃ¡ republika 2012', ''),
('20120521194500 +0200', '20120521195000 +0200', 'jednotka_sk', 'PoÄasie', 'SlovenskÃ¡ republika 2012', ''),
('20120521195000 +0200', '20120521201000 +0200', 'jednotka_sk', 'GÃ³ly - body - sekundy', 'SlovenskÃ¡ republika 2012', ''),
('20120521201000 +0200', '20120521214500 +0200', 'jednotka_sk', 'Nebo na dosah', 'RomantickÃ½ film StarÃ¡ lÃ¡ska nehrdzavie... RÃ©Å¾ia: Tonie Marshall ÃšÄinkujÃº: Catherine Deneuve, William Hurt (FrancÃºzsko, Kanada, Å panielsko 2002)', ''),
('20120521214500 +0200', '20120521221500 +0200', 'jednotka_sk', 'ReportÃ©ri', 'AktuÃ¡lna publicistika. VÅ¡ade tam, kde zlyhÃ¡va zÃ¡kon a nespravodlivosÅ¥ ostÃ¡va nepotrestanÃ¡, prichÃ¡dzajÃº reportÃ©ri, aby priniesli svedectvo o dobe, ktorÃº Å¾ijeme... (SlovenskÃ¡ republika 2012)', ''),
('20120521221500 +0200', '20120521222500 +0200', 'jednotka_sk', 'NoÄnÃ© sprÃ¡vy', 'SlovenskÃ¡ republika 2012', ''),
('20120521222500 +0200', '20120521222800 +0200', 'jednotka_sk', 'PoÄasie', 'SlovenskÃ¡ republika 2012', ''),
('20120521222800 +0200', '20120521223500 +0200', 'jednotka_sk', 'GÃ³ly - body - sekundy', 'SlovenskÃ¡ republika 2012', ''),
('20120521223500 +0200', '20120521233000 +0200', 'jednotka_sk', 'HlavnÃ½ podozrivÃ½', 'KriminÃ¡lny seriÃ¡l. VynikajÃºca Helen Mirren v Ãºlohe skÃºsenej inÅ¡pektorky Jane Tennisonovej, ktorÃ¡ sa tvrdo snaÅ¾Ã­ presadiÅ¥ vo svete, v ktorom vÃ½razne dominujÃº muÅ¾i. RÃ©Å¾ia: Menaul Christopher ÃšÄinkujÃº: Helen Mirren, Tom Bell, John Benfield, John Bowe (VeÄ¾kÃ¡ BritÃ¡nia 1995)', ''),
('20120521233000 +0200', '20120522002500 +0200', 'jednotka_sk', 'BlÃ¡znivo zamilovanÃ­', 'VeÄnosÅ¥ RomantickÃ¡ komÃ©dia RÃ©Å¾ia: Riccardo Milani ÃšÄinkujÃº: Stefania Rocca, Emilio Solfrizzi, Liskova Antonia, Neri MarcorÃ© (Taliansko 2009)', '');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `uname` varchar(255) NOT NULL,
  `pass` varchar(128) NOT NULL,
  `email` varchar(128) NOT NULL,
  `fullname` varchar(120) NOT NULL,
  `menu` varchar(15) NOT NULL,
  `mychans` text NOT NULL,
  `invert_gravity` varchar(6) NOT NULL,
  `disable_gravity` varchar(6) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uname`, `pass`, `email`, `fullname`, `menu`, `mychans`, `invert_gravity`, `disable_gravity`) VALUES
('test', '098f6bcd4621d373cade4e832627b4f6', '', 'Daniel', 'menu1', 'animalplanet_sk,axn_sk,axncrime_sk,axndigi_sk,', 'false', 'false'),
('danman', 'd6581d542c7eaf801284f084478b5fcc', 'daniel.kucera@gmail.com', 'Daniel Kucera', 'menu1', 'markiza_sk,joj_sk,nova_sk,jednotka_sk,dvojka_sk,ct1_sk,ct2_sk,sport1_sk,sport2_sk,', 'false', 'true');

-- --------------------------------------------------------

--
-- Table structure for table `vod`
--

CREATE TABLE IF NOT EXISTS `vod` (
  `title` varchar(255) NOT NULL,
  `desc` varchar(1024) NOT NULL,
  `img` varchar(255) NOT NULL,
  `uri` varchar(512) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `vod`
--

INSERT INTO `vod` (`title`, `desc`, `img`, `uri`) VALUES
('The Expendables 2 (2012)', 'Mr. Church reunites the Expendables for what should be an easy paycheck, but when one of their men is murdered on the job, their quest for revenge puts them deep in enemy territory and up against an unexpected threat.', 'http://ia.media-imdb.com/images/M/MV5BMTUyOTU4Nzk3Ml5BMl5BanBnXkFtZTcwOTM5NjYxNw@@._V1._SY317_.jpg', 'rtsp://v6.cache7.c.youtube.com/CjYLENy73wIaLQlKxlmRZvF-tBMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYJyJxePTiKjPTww=/0/0/0/video.3gp'),
('The Smurfs (2011)', 'When the evil wizard Gargamel chases the tiny blue Smurfs out of their village, they tumble from their magical world into New York City.', 'http://ia.media-imdb.com/images/M/MV5BMTY4MDc2NzQ4MF5BMl5BanBnXkFtZTcwNDc5OTU2NA@@._V1._SY317_CR0,0,214,317_.jpg', 'rtsp://v2.cache8.c.youtube.com/CjYLENy73wIaLQnfrvClgmkQyhMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYJyJxePTiKjPTww=/0/0/0/video.3gp'),
('Crazy, Stupid, Love. (2011)', 'A middle-aged husband''s life changes dramatically when his wife asks him for a divorce. He seeks to rediscover his manhood with the help of a newfound friend, Jacob, learning to pick up girls at bars.', 'http://ia.media-imdb.com/images/M/MV5BMTg2MjkwMTM0NF5BMl5BanBnXkFtZTcwMzc4NDg2NQ@@._V1._SY317_.jpg', 'rtsp://v1.cache4.c.youtube.com/CjYLENy73wIaLQlPEgx6Y7yueBMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYJyJxePTiKjPTww=/0/0/0/video.3gp'),
('Prometheus (2012)', 'A team of explorers discover a clue to the origins of mankind on Earth, leading them on a journey to the darkest corners of the universe. There, they must fight a terrifying battle to save the future of the human race.', 'http://ia.media-imdb.com/images/M/MV5BMTk5OTY4MDQ2OV5BMl5BanBnXkFtZTcwMjU1MDYxNw@@._V1._SY317_.jpg', 'rtsp://v1.cache6.c.youtube.com/CjYLENy73wIaLQlZ2OecCxnhrxMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYJyJxePTiKjPTww=/0/0/0/video.3gp'),
('To Rome with Love (2012)', 'A story about a number of people in Italy, some American, some Italian, some residents, some visitors, and the romances and adventures and predicaments they get into.', 'http://ia.media-imdb.com/images/M/MV5BMTcwNTg4MDMxM15BMl5BanBnXkFtZTcwNjAzMzY3Nw@@._V1._SY317_.jpg', 'rtsp://v4.cache8.c.youtube.com/CjYLENy73wIaLQl_P60aq9iGWBMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYJyJxePTiKjPTww=/0/0/0/video.3gp'),
('Little Birds (2011)', 'Lily and Alison face a life-changing event after they leave their Salton Sea home and follow the boys they meet back to Los Angeles.', 'http://ia.media-imdb.com/images/M/MV5BMTM2MjYwNDQyMV5BMl5BanBnXkFtZTcwOTQ4OTM2Nw@@._V1._SY317_CR0,0,214,317_.jpg', 'rtsp://v6.cache3.c.youtube.com/CjYLENy73wIaLQl2YGN31rMd9BMYDSANFEIJbXYtZ29vZ2xlSARSBXdhdGNoYJyJxePTiKjPTww=/0/0/0/video.3gp');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
