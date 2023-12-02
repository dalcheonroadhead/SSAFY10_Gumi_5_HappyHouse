package com.ssafy.home.favorite.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ssafy.home.favorite.model.BrokerDto;
import com.ssafy.home.favorite.model.CommentDto;
import com.ssafy.home.favorite.model.FavoriteListDto;
import com.ssafy.home.favorite.model.HouseDto;
import com.ssafy.home.favorite.model.mapper.HouseMapper;

@Service
public class FavoriteServiceImpl implements FavoriteService {
	
	private HouseMapper houseMapper; 
	
	public FavoriteServiceImpl(HouseMapper houseMapper) {
		this.houseMapper = houseMapper;
	}

	@Override
	public FavoriteListDto checkFavoriteOne(FavoriteListDto favoriteListDto) throws SQLException {
		// TODO Auto-generated method stub
		return houseMapper.checkFavoriteOne(favoriteListDto);
	}
	
	@Override
	public HouseDto selectOneHome(HouseDto dto) throws SQLException {
		// TODO Auto-generated method stub
		return houseMapper.selectOneHome(dto);
	}

	@Override
	public int insertHome(HouseDto dto) throws SQLException {
		// TODO Auto-generated method stub
		return houseMapper.insertHome(dto);
	}

	@Override
	public int insertFavoriteList(FavoriteListDto favoriteListDto) throws SQLException {
		// TODO Auto-generated method stub
		return houseMapper.insertFavoriteList(favoriteListDto);
	}
	
	@Override
	public int deleteFavoriteList(FavoriteListDto favoriteListDto) throws SQLException {
	
		return houseMapper.deleteFavoriteList(favoriteListDto);
	}
	
	@Override
	public List<FavoriteListDto> selectFavoriteList(String pk) throws SQLException {
		// TODO Auto-generated method stub
		return houseMapper.selectFavoriteList(pk);
	}

	@Override
	public HouseDto selectFavoriteHome(FavoriteListDto favoriteListDto) throws SQLException {
		// TODO Auto-generated method stub
		return houseMapper.selectFavoriteHome(favoriteListDto);
	}

	@Override
	public int addHit(HouseDto houseDto) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BrokerDto searchBroker(Map map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addScore(String PK) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addComment(CommentDto commentDto) throws SQLException {
		// TODO Auto-generated method stub
		return houseMapper.addComment(commentDto);
	}

	@Override
	public List<CommentDto> showComments(HouseDto houseDto) throws SQLException {
		// TODO Auto-generated method stub
		return houseMapper.showComments(houseDto);
	}

	@Override
	public int updateLike(CommentDto commentDto) throws SQLException {
		// TODO Auto-generated method stub
		return houseMapper.updateLike(commentDto);
	}

	@Override
	public int updateHate(CommentDto commentDto) throws SQLException {
		// TODO Auto-generated method stub
		return houseMapper.updateHate(commentDto);
	}
}
