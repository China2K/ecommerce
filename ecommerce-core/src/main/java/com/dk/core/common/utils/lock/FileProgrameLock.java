package com.dk.core.common.utils.lock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

/**
 * 文件锁
 */
public class FileProgrameLock implements Lock
{

	private String		lockFileName	= null;
	FileChannel			channel			= null;
	private FileLock	lock			= null;

	public static Lock get(String fileName)
	{
		FileProgrameLock d = new FileProgrameLock(fileName);
		return (Lock) d;
	}

	public FileProgrameLock(String lockFileName)
	{
		this.lockFileName = lockFileName;
	}

	/**
	 * 检测是否被锁定-不建议使用
	 * 
	 * @return true被锁定 ,false空闲
	 */
	public boolean isLocked() throws FileNotFoundException
	{
		File tf = new File(lockFileName);
		if (!tf.exists())
		{
			return false;
		}
		FileChannel __channel = new RandomAccessFile(tf, "rw").getChannel();
		FileLock tl = null;
		try
		{
			tl = __channel.tryLock();
			if (tl == null)
			{
				return true;
			} else
			{
				return false;
			}
		} catch (OverlappingFileLockException e)
		{
			return true;
		} catch (IOException e)
		{
			return true;
		} catch (Exception e)
		{
			return true;
		} finally
		{
			try
			{
				if (tl != null)
				{
					tl.release();
				}
				tl = null;
				if (__channel.isOpen())
				{
					__channel.close();
				}
				__channel = null;
				tf = null;
			} catch (IOException e)
			{
				e.printStackTrace();
			}

		}
	}

	/**
	 * 获取锁资源
	 * 
	 * @return true成功锁定目标资源 ,false锁定操作失败
	 */
	public boolean obtain()
	{
		try
		{
			File tf = new File(lockFileName);
			createFile();
			channel = new RandomAccessFile(tf, "rw").getChannel();
			lock = channel.lock();
			return true;
		} catch (OverlappingFileLockException e)
		{
			return false;
		} catch (Exception e)
		{
			return false;
		}
	}

	/**
	 * 释放锁
	 */
	public void unlock()
	{
		try
		{
			if (lock != null)
			{
				lock.release();
			}
			if (channel != null && channel.isOpen())
			{
				channel.close();
			}
			lock = null;
			channel = null;
			this.deleteFile();
		} catch (IOException e)
		{
		}
	}

	protected void finalize() throws Throwable
	{
		super.finalize();
	}

	private void createFile() throws IOException
	{
		File tf = new File(lockFileName);
		if (!tf.exists())
		{
			tf.createNewFile();
		}
		tf = null;
	}

	private void deleteFile()
	{
		File tf = new File(lockFileName);
		if (tf.exists())
		{
			tf.delete();
		}
		tf = null;
	}

}
